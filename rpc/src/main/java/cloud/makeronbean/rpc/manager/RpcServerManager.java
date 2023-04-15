package cloud.makeronbean.rpc.manager;

import cloud.makeronbean.rpc.annotation.RpcService;
import cloud.makeronbean.rpc.annotation.RpcServiceScan;
import cloud.makeronbean.rpc.config.RpcConfig;
import cloud.makeronbean.rpc.factory.rpcService.RpcServiceContainer;
import cloud.makeronbean.rpc.factory.rpcService.RpcServiceContainerFactory;
import cloud.makeronbean.rpc.handler.HeartBearServerResponseHandler;
import cloud.makeronbean.rpc.handler.HeartBeatServerHandler;
import cloud.makeronbean.rpc.protocol.MessageCodecSharable;
import cloud.makeronbean.rpc.protocol.ProtocolFrameDecoder;
import cloud.makeronbean.rpc.handler.RpcRequestMessageHandler;
import cloud.makeronbean.rpc.utils.ClassScanUtil;
import cloud.makeronbean.rpc.utils.StringUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;

import java.util.Set;

/**
 * @author makeronbean
 * @createTime 2023-04-14  15:57
 * @description TODO 服务器
 */
public class RpcServerManager {
    private static ServerBootstrap serverBootstrap;
    protected Integer port;

    public RpcServerManager(Integer port) {
        this.port = port;
        start();
        serviceScan();
    }

    public RpcServerManager() {
        this.port = Integer.valueOf(RpcConfig.get("port"));
        start();
        serviceScan();
    }

    /**
     * 扫描 RpcServiceScan 注解指定的包
     */
    @SneakyThrows
    private void serviceScan() {
        StackTraceElement[] stack = new Throwable().getStackTrace();
        String className = stack[stack.length - 1].getClassName();
        Class<?> mainClazz = Class.forName(className);
        RpcServiceScan rpcServiceScan = mainClazz.getDeclaredAnnotation(RpcServiceScan.class);
        // 如果没有标识该注解
        if (rpcServiceScan == null) {
            throw new RuntimeException("获取扫描路径失败，使用包扫描注解了吗？");
        }
        // 获取扫描路径
        String basePackageName = rpcServiceScan.value();
        if (StringUtil.isEmpty(basePackageName)) {
            basePackageName = rpcServiceScan.basePackage();
            if (StringUtil.isEmpty(basePackageName)) {
                throw new RuntimeException("获取扫描路径失败，指定需要扫描的包了吗？");
            }
        }

        RpcServiceContainer container = RpcServiceContainerFactory.getRpcServiceContainer();
        // 获取到 Class 对象
        Set<Class> classSet = ClassScanUtil.getClass4Annotation(basePackageName, RpcService.class);
        // new 对象注入到容器
        for (Class aClass : classSet) {
            Object o = aClass.newInstance();
            container.addService(o);
        }

    }


    /**
     * 初始化 ServerBootstrap
     */
    private void start() {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            serverBootstrap = new ServerBootstrap();
            MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
            ProtocolFrameDecoder FRAME_DECODER = new ProtocolFrameDecoder();
            RpcRequestMessageHandler REQUEST_HANDLER = new RpcRequestMessageHandler();
            LoggingHandler LOG_HANDLER = new LoggingHandler();
            HeartBearServerResponseHandler PING_HANDLER = new HeartBearServerResponseHandler();



            serverBootstrap.group(boss, worker);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();

                    // 黏包、半包处理
                    pipeline.addLast(FRAME_DECODER);
                    // 自定义协议编码与解析
                    pipeline.addLast(MESSAGE_CODEC);
                    // 心跳监测
                    pipeline.addLast(new IdleStateHandler(15,0,0));
                    // 心跳监测处理
                    pipeline.addLast(new HeartBeatServerHandler());
                    // 日志
                    pipeline.addLast(LOG_HANDLER);
                    // RPC请求处理器
                    pipeline.addLast(REQUEST_HANDLER);
                    // 心跳包响应处理器
                    pipeline.addLast(PING_HANDLER);

                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(port);
            Channel channel = channelFuture.sync().channel();
            channel.closeFuture().addListener(f -> {
                boss.shutdownGracefully();
                worker.shutdownGracefully();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
