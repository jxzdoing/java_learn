
package com.jxzdoing.rpccore.rpcdemocore.netty.common;

import lombok.Data;

@Data
public class RpcProtocol {

    private int len;

    private byte[] content;
}
