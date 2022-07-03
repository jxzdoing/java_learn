
package com.jxzdoing.rpccore.rpcdemocore.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;

import com.jxzdoing.rpccore.rpcdemocore.api.RpcRequest;
import com.jxzdoing.rpccore.rpcdemocore.api.RpcResponse;
import com.jxzdoing.rpccore.rpcdemocore.netty.client.RpcNettyClientSync;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

@Slf4j
public class RpcInvocationHandler implements InvocationHandler, MethodInterceptor {

    private final Class<?> serviceClass;
    private final String url;

    <T> RpcInvocationHandler(Class<T> serviceClass, String url) {
        this.serviceClass = serviceClass;
        this.url = url;
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        return process(serviceClass, method, args, url);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) {
        return process(serviceClass, method, args, url);
    }

    private Object process(Class<?> service, Method method, Object[] params, String url) {

        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setServiceClass(service.getName());
        rpcRequest.setMethod(method.getName());
        rpcRequest.setArgv(params);

        RpcResponse rpcResponse;
        try {
            rpcResponse = RpcNettyClientSync.getInstance().getResponse(rpcRequest, url);
        } catch (InterruptedException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        assert rpcResponse != null;
        if (!rpcResponse.getStatus()) {
            rpcResponse.getException().printStackTrace();
            return null;
        }

        return JSON.parse(rpcResponse.getResult().toString());
    }
}
