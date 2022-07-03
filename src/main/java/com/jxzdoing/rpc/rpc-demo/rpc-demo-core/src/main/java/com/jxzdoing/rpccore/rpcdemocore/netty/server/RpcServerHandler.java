

package com.jxzdoing.rpccore.rpcdemocore.netty.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import com.jxzdoing.rpccore.rpcdemocore.api.RpcRequest;
import com.jxzdoing.rpccore.rpcdemocore.api.RpcResponse;
import com.jxzdoing.rpccore.rpcdemocore.exception.CustomException;
import com.jxzdoing.rpccore.rpcdemocore.netty.common.RpcProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;


@Slf4j
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcProtocol> {

    private ApplicationContext applicationContext;

    RpcServerHandler(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol msg) throws Exception {
        RpcRequest rpcRequest = JSON.parseObject(new String(msg.getContent(), CharsetUtil.UTF_8),
                RpcRequest.class);
        RpcResponse response = invoke(rpcRequest);
        RpcProtocol message = new RpcProtocol();
        String requestJson = JSON.toJSONString(response);
        message.setLen(requestJson.getBytes(CharsetUtil.UTF_8).length);
        message.setContent(requestJson.getBytes(CharsetUtil.UTF_8));

        channelHandlerContext.writeAndFlush(message).sync();
    }

    private RpcResponse invoke(RpcRequest request) {
        RpcResponse response = new RpcResponse();
        String serviceClass = request.getServiceClass();

        Object service = applicationContext.getBean(serviceClass);

        try {
            Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
            Object result = method.invoke(service, request.getArgv());
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
            return response;
        } catch ( IllegalAccessException | InvocationTargetException | CustomException e) {
            e.printStackTrace();
            response.setException(e);
            response.setStatus(false);
            return response;
        }
    }

    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }
}
