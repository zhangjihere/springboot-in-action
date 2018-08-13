package org.tombear.spring.boot.blog.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <P>The entity represent authority</P>
 *
 * @author tombear on 2018-08-12 22:54.
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
public class Authority implements GrantedAuthority {
    private static final long serialVersionUID = 4304353156012280467L;

    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long id;    // 用户唯一标识

    @Column(nullable = false)   // 映射为字段，值不能为空
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
