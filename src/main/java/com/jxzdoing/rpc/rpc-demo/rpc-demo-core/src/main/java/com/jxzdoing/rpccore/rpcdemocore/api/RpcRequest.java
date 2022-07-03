package com.jxzdoing.rpccore.rpcdemocore.api;

import lombok.Data;

@Data
public class RpcRequest {

    private String serviceClass;


    private String method;


    private Object[] argv;
}
