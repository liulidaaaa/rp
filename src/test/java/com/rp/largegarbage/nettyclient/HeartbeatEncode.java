package com.rp.largegarbage.nettyclient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/7 21:53
 */
public class HeartbeatEncode extends MessageToByteEncoder<CustomProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, CustomProtocol msg, ByteBuf out) throws Exception {

        out.writeLong(msg.getId()) ;
        out.writeBytes(msg.getContent().getBytes()) ;

    }
}
