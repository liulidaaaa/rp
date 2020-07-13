package com.rp.largegarbage.nettyclient;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 客户端处理类
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/7 17:50
 */
@Slf4j
@ChannelHandler.Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 计算有多少客户端接入，第一个string为客户端ip
     */
    private static final ConcurrentHashMap<ChannelId, ChannelHandlerContext> CLIENT_MAP = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        CLIENT_MAP.put(ctx.channel().id(), ctx);
        log.info("ClientHandler Active");
    }

    /**
     * @param ctx
     * @DESCRIPTION: 有服务端端终止连接服务器会触发此函数
     * @return: void
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ctx.close();
        log.info("服务端终止了服务");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("客户端接收服务端发来的请求并处理" + msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //cause.printStackTrace();
        log.info("服务端发生异常(" + cause.getMessage() + ")");
        ctx.close();
    }

    /**
     * @param msg       需要发送的消息内容
     * @param channelId 连接通道唯一id
     * @DESCRIPTION: 客户端给服务端发送消息
     * @return: void
     */
    public void channelWrite(ChannelId channelId, String msg) {
        ChannelHandlerContext ctx = CLIENT_MAP.get(channelId);
        if (ctx == null) {
            log.info("通道（" + channelId + "）不存在");
            return;
        }
        //将客户端的信息直接返回写入ctx
        ctx.write(msg);
        //刷新缓存区
        ctx.flush();
    }
}
