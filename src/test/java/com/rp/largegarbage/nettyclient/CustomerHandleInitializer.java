package com.rp.largegarbage.nettyclient;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/7 22:10
 */
public class CustomerHandleInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                //30 秒没发送消息 将IdleStateHandler 添加到 ChannelPipeline 中
                .addLast(new IdleStateHandler(0, 30, 0))
                .addLast(new HeartbeatEncode())
                .addLast(new EchoClientHandle());
    }
}
