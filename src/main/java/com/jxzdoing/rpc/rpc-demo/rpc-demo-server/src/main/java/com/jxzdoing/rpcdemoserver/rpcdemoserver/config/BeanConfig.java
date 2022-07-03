package com.jxzdoing.rpcdemoserver.rpcdemoserver.config;


import com.jxzdoing.rpcdemoapi.rpcdemoapi.service.OrderService;
import com.jxzdoing.rpcdemoapi.rpcdemoapi.service.UserService;
import com.jxzdoing.rpcdemoserver.rpcdemoserver.impl.OrderServiceImpl;
import com.jxzdoing.rpcdemoserver.rpcdemoserver.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfig {

    @Bean("com.example.demo.service.UserService")
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean("com.example.demo.service.OrderService")
    public OrderService orderService() {
        return new OrderServiceImpl();
    }
}
