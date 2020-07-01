package com.rp.largegarbage.service;

import com.rp.largegarbage.entity.User;
import org.springframework.transaction.annotation.Transactional;


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

    /**
     * 移动端用户通过手机号登及验证短信注册并登录，返回用户及权限信息
     */
    public User registAndLogin(Integer phoneNo, String verificationCode, Integer IDCard);
    /**
     * 发送短信验证码
     */
    public void sendMessage(Integer phoneNo, String message);

}
