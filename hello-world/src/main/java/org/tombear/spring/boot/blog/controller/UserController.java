package org.tombear.spring.boot.blog.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tombear.spring.boot.blog.domain.User;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-07-24 22:10.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @RequestMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        System.out.println("id = [" + id + "]");
        return User.of(id, "tombear", "zhangjihere@hotmail.com");
    }
}
