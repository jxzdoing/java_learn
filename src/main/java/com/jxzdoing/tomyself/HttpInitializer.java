package com.jxzdoing.tomyself;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author:jack
 * @date:Create on 2022/5/12 15:59
 */
public class HttpInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpServerCodec());//解码器
        pipeline.addLast(new HttpObjectAggregator(1024*1024));//解析post请求body
        pipeline.addLast(new HttpHandler());//处理handler

    }
}
