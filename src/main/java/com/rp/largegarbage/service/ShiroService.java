package com.rp.largegarbage.service;

import com.rp.largegarbage.entity.Token;
import com.rp.largegarbage.entity.User;
import org.springframework.stereotype.Service;
import java.util.Map;



public interface ShiroService {

    //查询用户信息
    User findByUsername(String username);

    //生成token，并保存到数据库
    Map<String, Object> createToken(Integer userId);

    //登出
    void logout(String token);

    Token findByToken(String accessToken);

    User findByUserId(Integer userId);
}
