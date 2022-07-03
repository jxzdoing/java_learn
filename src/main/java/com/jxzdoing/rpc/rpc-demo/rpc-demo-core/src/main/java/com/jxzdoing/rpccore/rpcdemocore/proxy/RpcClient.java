

package com.jxzdoing.rpccore.rpcdemocore.proxy;


public interface RpcClient {

    <T> T create(final Class<T> serviceClass, final String url);
}
