package org.tombear.spring.boot.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.tombear.spring.boot.blog.domain.User;
import org.tombear.spring.boot.blog.repository.UserRepository;

import java.util.Optional;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-07-24 22:10.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * query all users
     */
    @GetMapping
    public ModelAndView list(Model model) {
        System.out.println("UserController.list");
        model.addAttribute("userList", userRepository.findAll());
        model.addAttribute("title", "用户管理");
        return new ModelAndView("users/list", "userModel", model);
    }

    /**
     * query User by id
     */
    @GetMapping("{id}")
    public ModelAndView getUser(@PathVariable("id") Long id, Model model) {
        System.out.println("UserController.getUser");
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user.orElse(null));
        model.addAttribute("title", "产看用户");
        return new ModelAndView("users/view", "userModel", model);
    }

    /**
     * 获取创建表单页面
     */
    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        System.out.println("UserController.createForm");
        model.addAttribute("user", new User(null, null, null));
        model.addAttribute("title", "创建用户");
        return new ModelAndView("users/form", "userModel", model);
    }

    /**
     * 保存用户
     */
    @PostMapping
    public ModelAndView saveOrUpdateUser(User user) {
        System.out.println("UserController.saveOrUpdateUser");
        userRepository.save(user);
        return new ModelAndView("redirect:/users");
    }

    /**
     * 删除用户
     */
    @GetMapping(value = "delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Long id) {
        System.out.println("UserController.deleteUser");
        userRepository.deleteById(id);
        return new ModelAndView("redirect:/users");
    }

    /**
     * 获取修改用户页面
     */
    @GetMapping("modify/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        System.out.println("UserController.modifyForm");
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user.orElse(null));
        model.addAttribute("title", "修改用户");
        return new ModelAndView("users/form", "userModel", model);
    }
}
