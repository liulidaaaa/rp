package com.rp.largegarbage.nettyclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 发送的心跳信息是一个单例的 Bean
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/7 21:36
 */
@Configuration
public class HeartBeatConfig {

    @Value("${channel.id}")
    private long id ;


    @Bean(value = "heartBeat")
    public CustomProtocol heartBeat(){
        return new CustomProtocol(id,"ping") ;
    }
}
