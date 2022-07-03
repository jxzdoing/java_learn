package com.jxzdoing.rpcdemoclient.rpcdemoclient;

import com.jxzdoing.rpccore.rpcdemocore.proxy.RpcByteBuddy;
import com.jxzdoing.rpccore.rpcdemocore.proxy.RpcClient;
import com.jxzdoing.rpccore.rpcdemocore.proxy.RpcClientJdk;
import com.jxzdoing.rpcdemoapi.rpcdemoapi.model.Order;
import com.jxzdoing.rpcdemoapi.rpcdemoapi.model.User;
import com.jxzdoing.rpcdemoapi.rpcdemoapi.service.OrderService;
import com.jxzdoing.rpcdemoapi.rpcdemoapi.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcDemoClientApplication {

    public static void main(String[] args) {

        RpcClient jdk = new RpcClientJdk();
        UserService userService = jdk.create(UserService.class, "http://localhost:8080/");
        User user = userService.findById(1);
        if (user == null) {
            return;
        }

        RpcClient buddy = new RpcByteBuddy();
        OrderService orderService = buddy.create(OrderService.class, "http://localhost:8080/");
        Order order = orderService.findById(1992129);
        if (order == null) {
            return;
        }
        System.out.println(String.format("find order name=%s, user=%d", order.getName(), order.getUserId()));
    }
}
