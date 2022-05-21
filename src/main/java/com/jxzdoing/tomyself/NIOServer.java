package com.jxzdoing.tomyself;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author:jack
 * @date:Create on 2022/5/12 15:11
 */
public class NIOServer {
    public static void main(String[] args) {
         final int port = 8801;
        EventLoopGroup boss = new NioEventLoopGroup(2);//接受客户端请求线程
        EventLoopGroup worker = new NioEventLoopGroup(16);//实际处理线程

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.option(ChannelOption.SO_BACKLOG,128)//服务端队列的大小
                .childOption(ChannelOption.TCP_NODELAY,true)//小数据及传送，不等到组装成大的数据包
                .childOption(ChannelOption.SO_KEEPALIVE,true)//心跳检测，默认7200s，2小时
                .childOption(ChannelOption.SO_REUSEADDR,true)//允许重复使用本地地址
                .childOption(ChannelOption.SO_RCVBUF,32*1024)//接收缓冲区的大小
                .childOption(ChannelOption.SO_SNDBUF,32*1024)//发送缓冲区的大小
                .childOption(EpollChannelOption.SO_REUSEPORT,true)//允许重复使用本地端口
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);//缓冲区

        serverBootstrap.group(boss,worker).channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new HttpInitializer());//初始化channel

        try {
            Channel channel =  serverBootstrap.bind(port).sync().channel();
            System.out.println("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + port + '/');
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }


    }
}
