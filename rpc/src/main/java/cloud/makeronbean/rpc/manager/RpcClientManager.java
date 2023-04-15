package cloud.makeronbean.rpc.manager;

import cloud.makeronbean.rpc.config.RpcConfig;
import cloud.makeronbean.rpc.handler.HeartBeatClientHandler;
import cloud.makeronbean.rpc.handler.RpcResponseMessageHandler;
import cloud.makeronbean.rpc.protocol.MessageCodecSharable;
import cloud.makeronbean.rpc.protocol.ProtocolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author makeronbean
 * @createTime 2023-04-15  08:53
 * @description TODO 客户端
 */
public class RpcClientManager {
    private String host;
    private Integer port;
    private Bootstrap bootstrap;
    private Channel channel;

    private static RpcClientManager rpcClientManager;

    private static final Object LOCK = new Object();

    public static RpcClientManager getInstance() {
        if (rpcClientManager != null) {
            return rpcClientManager;
        }
        synchronized (LOCK) {
            if (rpcClientManager != null) {
                return rpcClientManager;
            }
            rpcClientManager = new RpcClientManager();
        }
        return rpcClientManager;
    }

    private RpcClientManager() {
        host = RpcConfig.get("host");
        port = Integer.valueOf(RpcConfig.get("port"));
        init();
    }

    public RpcClientManager(String host, Integer port) {
        this.host = host;
        this.port = port;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        RpcResponseMessageHandler RPC_RESPONSE_HANDLER = new RpcResponseMessageHandler();
        LoggingHandler LOG_HANDLER = new LoggingHandler();
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    // 黏包半包
                    pipeline.addLast(new ProtocolFrameDecoder());
                    // 协议解析
                    pipeline.addLast(MESSAGE_CODEC);
                    // 心跳监测
                    pipeline.addLast(new IdleStateHandler(0,10,0));
                    // 发送心跳包
                    pipeline.addLast(new HeartBeatClientHandler());
                    // 日志
                    pipeline.addLast(LOG_HANDLER);
                    // RPC解析响应处理
                    pipeline.addLast(RPC_RESPONSE_HANDLER);
                }
            });
            channel = bootstrap.connect(host, port).sync().channel();
            channel.closeFuture().addListener(f -> group.shutdownGracefully());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取接口的代理对象
     */
    public <T> T getServiceImpl(Class<T> serviceClass) {
        return (T) ClientProxy.getServiceImpl(serviceClass, channel);
    }
}
