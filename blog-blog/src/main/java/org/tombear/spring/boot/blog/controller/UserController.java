package org.tombear.spring.boot.blog.controller;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.tombear.spring.boot.blog.domain.Authority;
import org.tombear.spring.boot.blog.domain.User;
import org.tombear.spring.boot.blog.service.AuthorityService;
import org.tombear.spring.boot.blog.service.UserService;
import org.tombear.spring.boot.blog.util.ConstraintViolationExceptionHandler;
import org.tombear.spring.boot.blog.vo.Response;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

/**
 * <P>Use to process REST API about user function</P>
 *
 * @author tombear on 2018-07-24 22:10.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthorityService authorityService;

    @Autowired
    public UserController(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }


    /**
     * query all users
     */
    @GetMapping
    public ModelAndView list(
            @RequestParam(value = "async", required = false) boolean async,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            Model model) {
        System.out.println("UserController.list");
        PageRequest pageable = PageRequest.of(pageIndex, pageSize);
        Page<User> page = userService.listUsersByNameLike(name, pageable);
        List<User> users = page.getContent();
        model.addAttribute("page", page);
        model.addAttribute("userList", users);
        model.addAttribute("title", "用户管理");
        return new ModelAndView(async ? "users/list :: #mainContainerRepleace" : "users/list", "userModel", model);
    }

    /**
     * 获取创建表单页面
     */
    @GetMapping("/add")
    public ModelAndView createForm(Model model) {
        System.out.println("UserController.createForm");
        model.addAttribute("user", new User(null, null, null,null));
        model.addAttribute("title", "创建用户");
        return new ModelAndView("users/add", "userModel", model);
    }

    /**
     * 保存或修改用户
     */
    @PostMapping
    public ResponseEntity<Response> saveOrUpdateUser(User user, Long authorityId) {
        System.out.println("UserController.saveOrUpdateUser");
        List<Authority> authorities = Lists.newArrayList();
        authorities.add(authorityService.getAuthorityById(authorityId).orElse(null));
        user.setUserAuthorities(authorities);

        try {
            userService.saveOrUpdateUser(user);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok(new Response(false, ConstraintViolationExceptionHandler.getMessage(e), null));
        }
        return ResponseEntity.ok(new Response(true, "处理成功", null));
    }

    /**
     * 删除用户
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Model model) {
        System.out.println("UserController.delete");
        try {
            userService.removeUser(id);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok(new Response(false, e.getMessage(), null));
        }
        return ResponseEntity.ok(new Response(true, "处理成功", null));
    }

    /**
     * 获取修改用户页面
     */
    @GetMapping("edit/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        System.out.println("UserController.modifyForm");
        Optional<User> user = userService.getUserById(id);
        model.addAttribute("user", user.orElse(null));
        model.addAttribute("title", "修改用户");
        return new ModelAndView("users/edit", "userModel", model);
    }

}
