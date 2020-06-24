package com.rp.largegarbage.service.impl;

import com.rp.largegarbage.dao.UserDao;
import com.rp.largegarbage.entity.User;
import com.rp.largegarbage.service.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/23 17:45
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    private UserDao userDao;


    //12小时后过期
    private final static int EXPIRE = 3600 * 12;

    /**
     * 根据username查找用户
     *
     * @param username
     * @return User
     */
    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        return user;
    }
    /**
     * 生成一个token
     *@param  userId
     *@return Result
     *//*
    @Override
    public Map<String, Object> createToken(Integer userId) {
        Map<String, Object> result = new HashMap<>();
        //生成一个token
        String token = CustomSessionIdGenerator.generateValue();
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        //判断是否生成过token
        Token tokenEntity = tokenDao.findByUserId(userId);
        if (tokenEntity == null) {
            tokenEntity = new Token();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //保存token
            tokenDao.save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //更新token
            tokenDao.save(tokenEntity);
        }
        result.put("token", token);
        result.put("expire", EXPIRE);
        return result;

    }*/

    /*@Override
    public void logout(String token) {
        Token byToken = findByToken(token);
        //生成一个token
        token = CustomSessionIdGenerator.generateId();
        //修改token
        Token tokenEntity = new Token();
        tokenEntity.setUserId(byToken.getUserId());
        tokenEntity.setToken(token);
        tokenDao.save(tokenEntity);
    }*/

    /*@Override
    public Token findByToken(String accessToken) {
        return tokenDao.findByToken(accessToken);

    }*/

    @Override
    public User findByUserId(Integer userId) {
        return userDao.findByUserId(userId);
    }

}
