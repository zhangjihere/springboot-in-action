package org.tombear.spring.boot.fileserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-08-18 18:30.
 */
@Configuration
//@EnableWebMvc
public class SecurityConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("*"); // permiss CORQ
    }
}