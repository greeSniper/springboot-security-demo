package com.tangzhe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by 唐哲
 * 2018-01-30 13:49
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserService myUserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("zhangsan").password("123456").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("demo").password("123456").roles("USER");

        //数据库管理用户，自定义密码验证
        auth.userDetailsService(myUserService).passwordEncoder(new MyPasswordEncoder());

        //使用Spring Security默认的表结构管理用户
        auth.jdbcAuthentication().usersByUsernameQuery("").authoritiesByUsernameQuery("").passwordEncoder(new MyPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll() //放行/请求
                .anyRequest().authenticated() //其它请求需要认证
                .and()
                .logout().permitAll() //放行登出
                .and()
                .formLogin(); //表单登录

        http.csrf().disable(); //关闭csrf防御
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //忽略静态文件的权限认证
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }

}
