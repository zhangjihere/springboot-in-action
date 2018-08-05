package org.tombear.spring.boot.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-07-22 23:38.
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello World! Welcome to visit tombear site";
    }
}
