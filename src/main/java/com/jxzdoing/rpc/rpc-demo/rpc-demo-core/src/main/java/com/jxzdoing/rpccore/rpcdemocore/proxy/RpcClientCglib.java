package com.jxzdoing.rpccore.rpcdemocore.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class RpcClientCglib extends RpcProxy implements RpcClient {

    @Override
    public <T> T create(Class<T> serviceClass, String url) {
        if (!isExit(serviceClass.getName())) {
            add(serviceClass.getName(), newProxy(serviceClass, url));
        }
        return (T) getProxy(serviceClass.getName());
    }

    private <T> T newProxy(Class<T> serviceClass, String url) {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new RpcInvocationHandler(serviceClass, url));
        enhancer.setSuperclass(serviceClass);
        return (T) enhancer.create();
    }
}
