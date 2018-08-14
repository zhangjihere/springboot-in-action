package org.tombear.spring.boot.blog.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <P>Use for user management menu option</P>
 *
 * @author tombear on 2018-08-12 19:40.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {
    private String name;    // 菜单名称
    private String url;     // 菜单Url
}
