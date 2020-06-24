package com.rp.largegarbage.service;

import com.rp.largegarbage.entity.User;


/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/23 17:45
 */
public interface ShiroService {

    //查询用户信息
    User findByUsername(String username);

    //生成token，并保存到数据库
    //Map<String, Object> createToken(Integer userId);

    //登出
    //void logout(String token);

    //Token findByToken(String accessToken);

    User findByUserId(Integer userId);
}
