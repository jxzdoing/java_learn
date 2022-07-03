
package com.jxzdoing.rpccore.rpcdemocore.netty.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {

    private int length = 0;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= 4) {
            if (length == 0) {
                length = in.readInt();
            }
            if (in.readableBytes() < length) {
                return;
            }
            byte[] content = new byte[length];
            if (in.readableBytes() >= length) {
                in.readBytes(content);
                RpcProtocol rpcProtocol = new RpcProtocol();
                rpcProtocol.setLen(length);
                rpcProtocol.setContent(content);
                out.add(rpcProtocol);
            }
            length = 0;
        }
    }
}
