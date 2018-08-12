package org.tombear.spring.boot.blog.controller;

import com.google.common.collect.Lists;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.tombear.spring.boot.blog.vo.Menu;

import java.util.List;

/**
 * <P>
 * 用于处理后台管理相关的控制 Use to process back control management function
 * </P>
 *
 * @author tombear on 2018-08-11 13:27.
 */
@Controller
@RequestMapping("/admins")
public class AdminController {

    /**
     * return to blogger management main page
     */
    @GetMapping
    public ModelAndView listUsers(Model model) {
        System.out.println("AdminController.listUsers");
        List<Menu> list = Lists.newArrayList();
        list.add(new Menu("用户列表", "/users")); // 目前仅有有一项，未来扩展需要写到数据库中
        model.addAttribute("list", list);
        return new ModelAndView("admins/index", "model", model);
    }
}
