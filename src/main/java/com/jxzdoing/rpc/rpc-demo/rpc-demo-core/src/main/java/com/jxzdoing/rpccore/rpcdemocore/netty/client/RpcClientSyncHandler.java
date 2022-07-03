package com.jxzdoing.rpccore.rpcdemocore.netty.client;


import com.alibaba.fastjson.JSON;
import com.jxzdoing.rpccore.rpcdemocore.api.RpcResponse;
import com.jxzdoing.rpccore.rpcdemocore.netty.common.RpcProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class RpcClientSyncHandler extends SimpleChannelInboundHandler<RpcProtocol> {

    private CountDownLatch latch;
    private RpcResponse response;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol msg) {
        RpcResponse rpcfxResponse = JSON.parseObject(new String(msg.getContent(), CharsetUtil.UTF_8),
                RpcResponse.class);
        response = rpcfxResponse;
        latch.countDown();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    RpcResponse getResponse() throws InterruptedException {
        latch.await();
        return response;
    }
}
