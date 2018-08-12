package org.tombear.spring.boot.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
     * return to blogger management index page
     */
    @GetMapping
    public ModelAndView listUsers(Model model) {
        return new ModelAndView("admins/index", "menuList", model);
    }
}
