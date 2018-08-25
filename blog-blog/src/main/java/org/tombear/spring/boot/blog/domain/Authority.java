package org.tombear.spring.boot.blog.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * <P>The entity represent authority</P>
 *
 * @author tombear on 2018-08-12 22:54.
 */
@Entity
@Data
public class Authority implements GrantedAuthority {
    private static final long serialVersionUID = 4304353156012280467L;

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    private Long id;    // authority primary key

    @Column(nullable = false)   // mapped to filed, value not empty
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
