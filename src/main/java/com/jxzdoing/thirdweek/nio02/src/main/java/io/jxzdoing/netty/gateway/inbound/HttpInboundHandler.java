package io.jxzdoing.netty.gateway.inbound;


import io.jxzdoing.netty.gateway.filter.HeaderHttpRequestFilter;
import io.jxzdoing.netty.gateway.filter.HttpRequestFilter;
import io.jxzdoing.netty.gateway.filter.MyFilter;
import io.jxzdoing.netty.gateway.outbound.httpclient4.HttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final List<String> proxyServer;
    private HttpOutboundHandler handler;
    private List<HttpRequestFilter> filterList = new ArrayList<>();


    public HttpInboundHandler(List<String> proxyServer) {
        this.proxyServer = proxyServer;
        this.handler = new HttpOutboundHandler(this.proxyServer);
        filterList.add(new HeaderHttpRequestFilter());
        filterList.add(new MyFilter());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            // 往BackendServer端的出站处理
            handler.handle(fullRequest, ctx, filterList);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
