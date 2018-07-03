package com.tangzhe;

import com.tangzhe.security.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 唐哲
 * 2018-01-30 13:45
 */
@SpringBootApplication
@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @RequestMapping("/")
    public String home() {
        return "hello Spring Security";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }

    //@EnableGlobalMethodSecurity(prePostEnabled = true)才能生效
    @PreAuthorize("hasRole('ROLE_ADMIN')") //只有ADMIN用户能访问
    @RequestMapping("/roleAuth")
    public String roleAuth() {
        return "role auth";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasAuthority('')") //方法调用前
    @PostAuthorize("hasRole('')") //方法调用后
    @PreFilter("") //过滤参数
    @PostFilter("") //过滤返回值
    @RequestMapping("/test")
    public String test() {
        return "test auth";
    }

    //id小于10
    @PreAuthorize("#id<10")
    @RequestMapping("/test")
    public String test(Integer id) {
        return "test auth";
    }

    //id小于10且当前登录用户名跟参数中的username相等
    @PreAuthorize("#id<10 or principal.username.equals(#username)")
    @RequestMapping("/test")
    public String test(Integer id, String username) {
        return "test auth";
    }

    //传入参数用户名为admin
    @PreAuthorize("#user.username.equals('admin')")
    @RequestMapping("/test")
    public String test(User user) {
        return "test auth";
    }

    //返回值是偶数
    @PostAuthorize("returnObject%2==0")
    @RequestMapping("/test2")
    public Integer test2(Integer id) {
        return id;
    }

    /**
     * 过滤
       idList能被2整除
       返回值能被4整除
     */
    @PreFilter("filterObject%2==0")
    @PostFilter("filterObject%4==0")
    @RequestMapping("/test2")
    public List<Integer> test2(List<Integer> ids) {
        return ids;
    }

}
