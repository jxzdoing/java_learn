

package com.jxzdoing.rpccore.rpcdemocore.netty.client;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jxzdoing.rpccore.rpcdemocore.api.RpcRequest;
import com.jxzdoing.rpccore.rpcdemocore.api.RpcResponse;
import com.jxzdoing.rpccore.rpcdemocore.netty.common.RpcProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;


public class RpcNettyClientSync {

    private enum EnumSingleton {

        INSTANCE;
        private RpcNettyClientSync instance;

        EnumSingleton(){
            instance = new RpcNettyClientSync();
        }
        public RpcNettyClientSync getSingleton(){
            return instance;
        }
    }

    public static RpcNettyClientSync getInstance(){
        return EnumSingleton.INSTANCE.getSingleton();
    }


    private ConcurrentHashMap<String, Channel> channelPool = new ConcurrentHashMap<>();
    private EventLoopGroup clientGroup = new NioEventLoopGroup(new ThreadFactoryBuilder().setNameFormat("client work-%d").build());

    private RpcNettyClientSync() {
    }

    public RpcResponse getResponse(RpcRequest rpcRequest, String url) throws InterruptedException,
            URISyntaxException {
        RpcProtocol request = convertNettyRequest(rpcRequest);

        URI uri = new URI(url);
        String cacheKey = uri.getHost() + ":" + uri.getPort();

        if (channelPool.containsKey(cacheKey)) {
            Channel channel = channelPool.get(cacheKey);
            if (!channel.isActive() || !channel.isWritable() || !channel.isOpen()) {
                System.out.println("CHANNEL unuse");
            } else {
                try {
                    RpcClientSyncHandler handler = new RpcClientSyncHandler();
                    handler.setLatch(new CountDownLatch(1));
                    channel.pipeline().replace("clientHandler", "clientHandler", handler);
                    channel.writeAndFlush(request).sync();
                    return handler.getResponse();
                } catch (Exception e) {
                    channel.close();
                    channelPool.remove(cacheKey);
                }
            }
        }

        RpcClientSyncHandler handler = new RpcClientSyncHandler();
        handler.setLatch(new CountDownLatch(1));

        Channel channel = createChannel(uri.getHost(), uri.getPort());
        channel.pipeline().replace("clientHandler", "clientHandler", handler);
        channelPool.put(cacheKey, channel);

        channel.writeAndFlush(request).sync();
        return handler.getResponse();
    }

    private Channel createChannel(String address, int port) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientGroup)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.AUTO_CLOSE, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioSocketChannel.class)
                .handler(new RpcClientInitializer());
        return bootstrap.connect(address, port).sync().channel();
    }


    private RpcProtocol convertNettyRequest(RpcRequest rpcRequest) {
        RpcProtocol request = new RpcProtocol();
        String requestJson = JSON.toJSONString(rpcRequest);
        request.setLen(requestJson.getBytes(CharsetUtil.UTF_8).length);
        request.setContent(requestJson.getBytes(CharsetUtil.UTF_8));
        return request;
    }


    public void destroy() {
        clientGroup.shutdownGracefully();
    }
}
