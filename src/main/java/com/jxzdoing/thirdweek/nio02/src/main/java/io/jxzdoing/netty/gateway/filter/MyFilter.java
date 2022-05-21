package io.jxzdoing.netty.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author:jack
 * @date:Create on 2022/5/21 21:48
 */
public class MyFilter implements HttpRequestFilter{
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
         fullRequest.headers().set("my_filter","my_filter");
    }
}
