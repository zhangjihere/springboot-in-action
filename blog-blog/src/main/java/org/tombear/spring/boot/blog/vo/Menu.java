package org.tombear.spring.boot.blog.vo;

import lombok.Value;

/**
 * <P>Use for user management menu option</P>
 *
 * @author tombear on 2018-08-12 19:40.
 */
@Value
public class Menu {
    private String name;    // 菜单名称
    private String url;     // 菜单Url

    public Menu(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
