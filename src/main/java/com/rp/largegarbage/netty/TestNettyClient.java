package com.rp.largegarbage.netty;

/**
 * @Description 模拟多客户端发送报文
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/7 17:50
 */
public class TestNettyClient {

    public static void main(String[] args) {

            new Thread(new NettyClient()).start();
    }
}
