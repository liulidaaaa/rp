package com.rp.largegarbage.nettyclient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description Netty 可以使用 IdleStateHandler 来实现连接管理，当连接空闲时间太长（没有发送、接收消息）时则会触发一个事件，我们便可在该事件中实现心跳机制
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/7 21:31
 */
public class EchoClientHandle extends SimpleChannelInboundHandler<ByteBuf> {

    private final static Logger LOGGER = LoggerFactory.getLogger(EchoClientHandle.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt ;

            if (idleStateEvent.state() == IdleState.WRITER_IDLE){
                //LOGGER.info("已经 30 秒没有发送信息！");
                //向服务端发送消息
                CustomProtocol heartBeat = SpringBeanFactory.getBean("heartBeat", CustomProtocol.class);
                ctx.writeAndFlush(heartBeat).addListener(ChannelFutureListener.CLOSE_ON_FAILURE) ;
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf in) throws Exception {
        //从服务端收到消息时被调用
        LOGGER.info("客户端收到消息={}",in.toString(CharsetUtil.UTF_8)) ;

    }
}
