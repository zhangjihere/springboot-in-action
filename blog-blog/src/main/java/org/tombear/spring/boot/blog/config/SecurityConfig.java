package org.tombear.spring.boot.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <P>Security Config</P>
 *
 * @author tombear on 2018-08-12 23:45.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)  // Enable method level security config
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String KEY = "zhangjihere";

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(@Qualifier("userServiceImpl") UserDetailsService userDetailsService,
                          @Qualifier("passwordEncoder") PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean("passwordEncoder")
    public static PasswordEncoder passwordEncoder() {   // static signature prevent circular dependency
//        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder(); // 使用BCrypt
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder); // 设置密码加密方式
        return authenticationProvider;
    }

    /**
     * Custom Configuration
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "'/index")
                .permitAll()    // 都可以访问
                .antMatchers("/h2-console/**").permitAll()  // 都可访问
                .antMatchers("/admins/**").hasRole("ADMIN") // 需要相应角色才可访问
                .and()
                .formLogin()    // 基于Form表单登录验证
                .loginPage("/login").failureUrl("/login-error") // 自定义表单登录的action，被ss拦截处理
                .defaultSuccessUrl("/index")    // 默认，通过验证后请求地址为"/"
//                .successForwardUrl("/index")  // 如果设置，Url需要显式支持Post方法
                .and().rememberMe().key(KEY)    // 启用remember me
                .and().exceptionHandling().accessDeniedPage("/403");    // 异常处理，拒绝访问则重定向到403页面

        http.csrf().ignoringAntMatchers("/h2-console/**");  // 禁用H2控制台的CSRF防护
        http.headers().frameOptions().sameOrigin();         // 允许来自同一来源的H2控制台的请求
    }

    /**
     * 认证信息管理
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, AuthenticationProvider authProvider) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authProvider);
    }
}
