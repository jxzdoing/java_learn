package com.jxzdoing.rpcdemoapi.rpcdemoapi.service;


import com.jxzdoing.rpcdemoapi.rpcdemoapi.model.Order;

public interface OrderService {

    Order findById(Integer id);

    Order findError();
}
