package com.ztech.oauth;

import com.ztech.impl.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * SecurityConfig配置
 * date 2020-03-23
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {



    /**
     * 提供密码模式支持
     * 这一步的配置是必不可少的，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
     * 没有这步spring boot自动注入AuthenticationManager会报错
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }


    @Bean
    @Override
    protected UserDetailsService userDetailsService() {

        /*InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();//对应密码模式，内存中新建2个用户；实际用数据库查询
        manager.createUser(User.withUsername("user_1").password("$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq").authorities("USER").build());
        manager.createUser(User.withUsername("user_2").password("$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq").authorities("USER").build());*/
        return new UserDetailServiceImpl();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers(HttpMethod.OPTIONS, "/**")
               .and()
               .cors()
               .and()
               .csrf().disable();
    }


//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        return new UserDetailsServiceImpl();
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new Md5PasswordEncoderImpl();
//    }
//
//    //

//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.requestMatchers().antMatchers(HttpMethod.OPTIONS, "/**")
//                .and()
//                .cors()
//                .and()
//                .csrf().disable();
//    }


}
