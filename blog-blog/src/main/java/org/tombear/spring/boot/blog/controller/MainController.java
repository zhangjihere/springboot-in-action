package org.tombear.spring.boot.blog.controller;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.tombear.spring.boot.blog.domain.Authority;
import org.tombear.spring.boot.blog.domain.User;
import org.tombear.spring.boot.blog.service.AuthorityService;
import org.tombear.spring.boot.blog.service.UserService;

import java.util.List;

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

    private static final Long ROLE_USER_AUTHORITY_ID = 2L;   // 用户权限（博主）

    private final UserService userService;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MainController(UserService userService,
                          AuthorityService authorityService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/")
    public String root() {
        System.out.println("MainController.root");
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        System.out.println("MainController.index");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("MainController.login");
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        System.out.println("MainController.loginError");
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
        List<Authority> authorities = Lists.newArrayList();
        authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID).orElse(null));
        user.setUserAuthorities((authorities));
        user.setEncodePassword(passwordEncoder.encode(user.getPassword()));
        userService.registerUser(user);
        return "redirect:/login";
    }
}
