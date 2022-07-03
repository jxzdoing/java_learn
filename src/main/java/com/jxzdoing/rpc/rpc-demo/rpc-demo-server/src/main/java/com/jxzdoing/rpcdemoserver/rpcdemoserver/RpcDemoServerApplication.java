package com.jxzdoing.rpcdemoserver.rpcdemoserver;

import com.jxzdoing.rpccore.rpcdemocore.netty.server.RpcNettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpcDemoServerApplication implements ApplicationRunner {

    @Autowired
    private RpcNettyServer rpcNettyServer;

    public static void main(String[] args) {
        SpringApplication.run(RpcDemoServerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            rpcNettyServer.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rpcNettyServer.destroy();
        }
    }
}
