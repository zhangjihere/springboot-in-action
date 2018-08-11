package org.tombear.spring.boot.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <P>
 * 用于处理博客相关处理, Use to process blog control function
 * </P>
 *
 * @author tombear on 2018-08-11 12:30.
 */
@Controller
@RequestMapping("/blogs")
public class BlogController {

    @GetMapping
    public String listBlogs(
            @RequestParam(value = "order", required = false, defaultValue = "new") String order,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        System.out.println("order = [" + order + "], keyword = [" + keyword + "]");
        return "redirect:/index?order=" + order + "&keyword=" + keyword;
    }

}
