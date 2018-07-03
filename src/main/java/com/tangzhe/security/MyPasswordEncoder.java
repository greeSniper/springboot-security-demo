package com.tangzhe.security;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义密码验证
 * Created by 唐哲
 * 2018-01-30 14:45
 */
public class MyPasswordEncoder implements PasswordEncoder {

    private final static String SALT = "123456";

    @Override
    public String encode(CharSequence rawSequence) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        return encoder.encodePassword(rawSequence.toString(), SALT);
    }

    @Override
    public boolean matches(CharSequence rawSequence, String encodedPassword) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        return encoder.isPasswordValid(encodedPassword, rawSequence.toString(), SALT);
    }

}
