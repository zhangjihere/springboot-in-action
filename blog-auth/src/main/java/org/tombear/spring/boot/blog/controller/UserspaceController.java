package org.tombear.spring.boot.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/{username}")
    public String userSpace(@PathVariable("username") String username) {
        System.out.println("username = [" + username + "]");
        return "/userspace/u";
    }

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
            return "/userspace/u";
        }
        System.out.println("order = " + order);
        System.out.println("selflink:" + "redirect:/u/" + username + "/blogs?order=" + order);
        return "/userspace/u";
    }

    @GetMapping("/{username}/blogs/{id}")
    public String listBlogsByOrder(@PathVariable("id") Long id) {
        System.out.println("blogId = " + id);
        return "/userspace/blog";
    }

    @GetMapping("/{username}/blogs/edit")
    public String editBlog() {
        return "/userspae/blogedit";
    }

}
