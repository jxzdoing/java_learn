package com.jxzdoing.rpcdemoapi.rpcdemoapi.model;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String name;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
