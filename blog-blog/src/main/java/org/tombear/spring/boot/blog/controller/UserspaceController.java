package org.tombear.spring.boot.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.tombear.spring.boot.blog.domain.Blog;
import org.tombear.spring.boot.blog.domain.User;
import org.tombear.spring.boot.blog.service.BlogService;
import org.tombear.spring.boot.blog.service.UserService;
import org.tombear.spring.boot.blog.util.ConstraintViolationExceptionHandler;
import org.tombear.spring.boot.blog.vo.Response;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

/**
 * <P>Use to process userspace control function</P>
 *
 * @author tombear on 2018-08-11 12:59.
 */
@Controller
@RequestMapping("/u")
public class UserspaceController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final BlogService blogService;

    @Value("${file.server.url}")
    private String fileServerUrl;

    @Autowired
    public UserspaceController(UserService userService,
                               PasswordEncoder passwordEncoder,
                               BlogService blogService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.blogService = blogService;
    }


    /**
     * User main page
     *
     * @param username user's account identity
     */
    @GetMapping("/{username}")
    public String userSpace(@PathVariable("username") String username, Model model) {
        System.out.println("UserspaceController.userSpace, username = [" + username + "]");
        User user = (User) userService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return "redirect:/u/" + username + "/blogs";
    }

    /**
     * Get user profile and setting page
     */
    @GetMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")  // just only user can access method
    public ModelAndView profile(@PathVariable("username") String username, Model model) {
        System.out.println("UserspaceController.profile, username = [" + username + "], model = [" + model + "]");
        User user = (User) userService.loadUserByUsername(username);
        model.addAttribute("user", user)
                .addAttribute("fileServerUrl", fileServerUrl); // 文件服务器的地址返回给客户端
        return new ModelAndView("userspace/profile", "userModel", model);
    }

    /**
     * Save user profile setting
     */
    @PostMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public String saveProfile(@PathVariable("username") String username, User user) {
        System.out.println("UserspaceController.saveProfile, +username = [" + username + "], user = [" + user + "]");
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
        System.out.println("UserspaceController.avatar, username = [" + username + "], model = [" + model + "]");
        User user = (User) userService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return new ModelAndView("userspace/avatar", "userModel", model);
    }

    /**
     * Save avatar
     */
    @PostMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
        System.out.println("UserspaceController.saveAvatar, username = [" + username + "], user = [" + user + "]");
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
     * Find user's blogs
     */
    @GetMapping("/{username}/blogs")
    public String listBlogsByOrder(@PathVariable("username") String username, Model model,
                                   @RequestParam(value = "order", required = false, defaultValue = "new") String order,
                                   @RequestParam(value = "catalog", required = false) Long catalogId,
                                   @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                   @RequestParam(value = "async", required = false) boolean async,
                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        System.out.println("UserspaceController.listBlogsByOrder, username = [" + username + "], model = [" + model + "], order = [" + order + "], catalogId = [" + catalogId + "], keyword = [" + keyword + "], async = [" + async + "], pageIndex = [" + pageIndex + "], pageSize = [" + pageSize + "]");
        User user = (User) userService.loadUserByUsername(username);
        Page<Blog> page = null;
        if (catalogId != null && catalogId > 0) {
            // TODO
        } else if (order.equals("hot")) {    // hottest find
            Sort sort = new Sort(Sort.Direction.DESC, "readSize", "commentSize");
            Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
            page = blogService.listBlogsByTitleVoteAndSort(user, keyword, pageable);
        } else if (order.equals("new")) {   // newest find
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = blogService.listBlogsByTitleVote(user, keyword, pageable);
        }
        List<Blog> list = page.getContent();
        model.addAttribute("user", user)
                .addAttribute("order", order)
                .addAttribute("catalogId", catalogId)
                .addAttribute("keyword", keyword)
                .addAttribute("page", page)
                .addAttribute("blogList", list);
        return (async ? "userspace/u :: #mainContainerRepleace" : "userspace/u");
    }

    /**
     * Get blog display page
     */
    @GetMapping("/{username}/blogs/{id}")
    public String getBlogById(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        System.out.println("UserspaceController.getBlogById, username = [" + username + "], id = [" + id + "], model = [" + model + "]");
        Optional<Blog> optionalBlog = blogService.getBlogById(id);
        blogService.readingIncrease(id);    // increase 1 each reading time
        boolean isBlogOwner = false; // justify whether or not user is the blog's owner
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User principal = (User) authentication.getPrincipal();
            if (principal != null) {
                if (principal.getUsername().equals(username)) {
                    isBlogOwner = true;
                } else if (principal.toString().equals("anonymousUser")) {
                    System.out.println("principal is anonymousUser\n");
                } else {
                    System.out.printf("principal is %s\n", principal.toString());
                }
            }
        }
        model.addAttribute("isBlogOwner", isBlogOwner)
                .addAttribute("blogModel", optionalBlog.orElse(null));
        return "userspace/blog";
    }

    /**
     * Get page to add new blog
     */
    @GetMapping("/{username}/blogs/edit")
    public ModelAndView editBlog(Model model) {
        System.out.println("UserspaceController.editBlog, model = [" + model + "]");
        model.addAttribute("blog", new Blog(null, null, null));
        model.addAttribute("fileServerUrl", fileServerUrl);
        return new ModelAndView("userspace/blogedit", "blogModel", model);
    }

    /**
     * Get page to modify blog
     */
    @GetMapping("/{username}/blogs/edit/{id}")
    public ModelAndView editBlog(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        System.out.println("UserspaceController.editBlog, username = [" + username + "], id = [" + id + "], model = [" + model + "]");
        model.addAttribute("blog", blogService.getBlogById(id).get())
                .addAttribute("fileServerUrl", fileServerUrl);  // return fileserver url to client
        return new ModelAndView("userspace/blogedit", "blogModel", model);
    }

    /**
     * Save blog
     */
    @PostMapping("/{username}/blogs/edit")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {
        System.out.println("UserspaceController.saveBlog, username = [" + username + "], blog = [" + blog + "]");
        try {
            if (blog.getId() != null) { // Modify or new add
                Optional<Blog> optionalBlog = blogService.getBlogById(blog.getId());
                if (optionalBlog.isPresent()) {
                    Blog originBlog = optionalBlog.get();
                    originBlog.setTitle(blog.getTitle());
                    originBlog.setContent(blog.getContent());
                    originBlog.setSummary(blog.getSummary());
                    blogService.saveBLog(originBlog);
                }
            } else {
                User user = (User) userService.loadUserByUsername(username);
                blog.setUser(user);
                blogService.saveBLog(blog);
            }
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
        return ResponseEntity.ok().body(new Response(true, "Save blog OK!", redirectUrl));
    }

    /**
     * Delete a blog
     */
    @DeleteMapping("/{username}/blogs/{id}")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> deleteBlog(@PathVariable("username") String username, @PathVariable("id") Long id) {
        System.out.println("UserspaceController.deleteBlog, username = [" + username + "], id = [" + id + "]");
        try {
            blogService.removeBlog(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        String redirectUrl = "/u/" + username + "/blogs";
        return ResponseEntity.ok().body(new Response(true, "Delete blog OK!", redirectUrl));
    }

}
