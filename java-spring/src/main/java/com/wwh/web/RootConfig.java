package com.wwh.web;

import com.wwh.spring.SpringDao;
import com.wwh.spring.SpringService;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.wwh.controller")
@PropertySource("classpath:jdbc.properties")
@EnableAspectJAutoProxy
@EnableWebMvc
@Import({SpringDao.class, SpringService.class})
public class RootConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer sourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
