package com.jxzdoing.rpcdemoserver.rpcdemoserver.impl;


import com.jxzdoing.rpcdemoapi.rpcdemoapi.model.User;
import com.jxzdoing.rpcdemoapi.rpcdemoapi.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User findById(Integer id) {
        return new User(id, "RPC");
    }
}
