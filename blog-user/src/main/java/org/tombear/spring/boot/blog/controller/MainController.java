package org.tombear.spring.boot.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.tombear.spring.boot.blog.domain.User;
import org.tombear.spring.boot.blog.service.UserService;

/**
 * <P>
 * Use to process system's control function including login/out, register and so on
 * 用于处理整个系统相关的控制，包括登录退出、注册等功能
 * </P>
 *
 * @author tombear on 2018-08-11 12:10.
 */
@Controller
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登录失败，用户名或者密码错误！");
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        System.out.println("MainController.register");
        return "register";
    }

    /**
     * 注册用户
     */
    @PostMapping("/register")
    public String registerUser(User user) {
        System.out.println("MainController.registerUser");
        userService.saveOrUpdateUser(user);
        return "redirect:/login";
    }
}
