package com.jxzdoing.rpcdemoserver.rpcdemoserver.impl;


import com.jxzdoing.rpccore.rpcdemocore.exception.CustomException;
import com.jxzdoing.rpcdemoapi.rpcdemoapi.model.Order;
import com.jxzdoing.rpcdemoapi.rpcdemoapi.service.OrderService;

public class OrderServiceImpl implements OrderService {

    @Override
    public Order findById(Integer id) {
        return new Order(1, "RPC", 1);
    }

    @Override
    public Order findError() {
        throw new CustomException("Custom exception");
    }
}
