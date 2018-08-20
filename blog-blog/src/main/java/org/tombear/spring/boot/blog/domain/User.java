package org.tombear.spring.boot.blog.domain;

import com.google.common.collect.Lists;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <P>
 * 用户实体 User Entity including authority
 * </P>
 *
 * @author tombear on 2018-07-24 21:59.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@Entity
public class User implements UserDetails {
    private static final long serialVersionUID = -473324488593470975L;

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto incremental
    private Long id; // entity only identity

    @NotEmpty(message = "姓名不能为空")
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20) // map to field and value not null
    private String name;

    @NotEmpty(message = "邮箱不能为空")
    @Size(max = 50)
    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @NotEmpty(message = "账号不能为空")
    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 20, unique = true)
    private String username; // user account, only identity for login

    @NotEmpty(message = "密码不能为空")
    @Size(max = 100)
    @Column(length = 100)
    private String password; // password for login

    @Column(length = 200)
    private String avatar; // avatar image url

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> userAuthorities;

    /**
     * Returns the userAuthorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the userAuthorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // need to tranform List<Authority> to List<SimpleGrantedAuthority>,
        // or frontend can't get role list names
        List<SimpleGrantedAuthority> simpleAuthorities = Lists.newArrayList();
        for (GrantedAuthority authority : this.userAuthorities) {
            simpleAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return simpleAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
