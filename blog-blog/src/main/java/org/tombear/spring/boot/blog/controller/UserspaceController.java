package org.tombear.spring.boot.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.tombear.spring.boot.blog.domain.User;
import org.tombear.spring.boot.blog.service.UserService;
import org.tombear.spring.boot.blog.vo.Response;

import java.util.Optional;

/**
 * <P>
 * 用于处理用户空间相关控制, Use to process userspace control function
 * </P>
 *
 * @author tombear on 2018-08-11 12:59.
 */
@Controller
@RequestMapping("/u")
public class UserspaceController {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${file.server.url}")
    private String fileServerUrl;

    @Autowired
    public UserspaceController(@Qualifier("userServiceImpl") UserDetailsService userDetailsService,
                               UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public String userSpace(@PathVariable("username") String username) {
        System.out.println("username = [" + username + "]");
        return "userspace/u";
    }

    /**
     * 获取个人设置页面
     */
    @GetMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")  // just only user can access method
    public ModelAndView profile(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("fileServerUrl", fileServerUrl); // 文件服务器的地址返回给客户端
        return new ModelAndView("userspace/profile", "userModel", model);
    }

    /**
     * 保存个人设置
     */
    @PostMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public String saveProfile(@PathVariable("username") String username, User user) {
        System.out.println("UserspaceController.saveProfile");
        Optional<User> u = userService.getUserById(user.getId());
        if (u.isPresent()) {
            User originUser = u.get();
            originUser.setEmail(user.getEmail());
            originUser.setName(user.getName());
            String newRawPW = user.getPassword();
            boolean isMatch = passwordEncoder.matches(newRawPW, originUser.getPassword());
            if (isMatch) {  // 判断密码是否变更
                originUser.setEncodePassword(passwordEncoder.encode(newRawPW));
            }
            userService.saveOrUpdateUser(originUser);
        } else {
            System.out.println("Can't find match originUser.");
        }
        return "redirect:/u/" + username + "/profile";
    }

    /**
     * Get the page for editing the avatar
     */
    @GetMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView avatar(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return new ModelAndView("userspace/avatar", "userModel", model);
    }

    /**
     * Save avatar
     */
    @PostMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
        String avatarUrl = user.getAvatar();
        Optional<User> ou = userService.getUserById(user.getId());
        if (ou.isPresent()) {
            User originUser = ou.get();
            originUser.setAvatar(avatarUrl);
            userService.saveOrUpdateUser(originUser);
            return ResponseEntity.ok().body(new Response(true, "Save Avatar OK!", avatarUrl));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(false, "Save Avatar Failure!", username));
        }
    }

    /**
     * 查询用户相关的博客
     */
    @GetMapping("/{username}/blogs")
    public String listBlogsByOrder(
            @PathVariable("username") String username,
            @RequestParam(value = "order", required = false, defaultValue = "new") String order,
            @RequestParam(value = "category", required = false) Long category,
            @RequestParam(value = "keyword", required = false) String keyword) {
        if (category != null) {
            System.out.println("category = " + category);
            System.out.println("selflink:" + "redirect:/u/" + username + "/blogs?category=" + category);
        } else if (keyword != null && !keyword.isEmpty()) {
            System.out.println("keyword = " + keyword);
            System.out.println("selflink:" + "redirect:/u/" + username + "/blogs?keyword=" + keyword);
            return "userspace/u";
        }
        System.out.println("order = " + order);
        System.out.println("selflink:" + "redirect:/u/" + username + "/blogs?order=" + order);
        return "userspace/u";
    }

    @GetMapping("/{username}/blogs/{id}")
    public String listBlogsByOrder(@PathVariable("id") Long id) {
        System.out.println("blogId = " + id);
        return "userspace/blog";
    }

    @GetMapping("/{username}/blogs/edit")
    public String editBlog() {
        return "userspae/blogedit";
    }

}
