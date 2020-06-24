package com.rp.largegarbage.config;


import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义AuthenticationToken类
 *
 * @Author lld
 * @Date
 * @Version 1.0
 */
public class CustomToken extends UsernamePasswordToken {

    private String token;

    public CustomToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }


}
