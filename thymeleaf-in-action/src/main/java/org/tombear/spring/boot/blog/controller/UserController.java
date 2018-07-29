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
        model.addAttribute("userList", userRepository.listUser());
        model.addAttribute("title", "用户管理");
        return new ModelAndView("users/list", "userModel", model);
    }

    /**
     * query User by id
     */
    @GetMapping("{id}")
    public ModelAndView getUser(@PathVariable("id") Long id, Model model) {
        User user = userRepository.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("title", "产看用户");
        System.out.println("id = [" + id + "]");
        return new ModelAndView("users/view", "userModel", model);
    }

    /**
     * 获取创建表单页面
     */
    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "创建用户");
        return new ModelAndView("users/form", "userModel", model);
    }

    /**
     * 保存用户
     */
    @PostMapping
    public ModelAndView saveOrUpdateUser(User user) {
        userRepository.saveOrUpdateUser(user);
        return new ModelAndView("redirect:/users");
    }

    /**
     * 删除用户
     */
    @GetMapping(value = "delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteUser(id);
        return new ModelAndView("redirect:/users");
    }

    /**
     * 获取修改用户页面
     */
    @GetMapping("modify/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        User user = userRepository.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("title", "修改用户");
        return new ModelAndView("users/form", "userModel", model);
    }
}
