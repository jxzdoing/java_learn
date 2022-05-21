package io.jxzdoing.netty.gateway;



import io.jxzdoing.netty.gateway.inbound.HttpInboundServer;

import java.util.Arrays;

public class NettyServerApplication {
    
    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "3.0.0";
    
    public static void main(String[] args) {
        //API网关的端口
        String proxyPort = System.getProperty("proxyPort","8888");
        //后端服务的真实地址
        String proxyServers = System.getProperty("proxyServers","http://localhost:8088");
        int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" starting...");
        //构建网关的服务类
        HttpInboundServer server = new HttpInboundServer(port, Arrays.asList(proxyServers.split(",")));
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" started at http://localhost:" + port + " for server:" + server);
        try {
            //启动拂去
            server.run();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
