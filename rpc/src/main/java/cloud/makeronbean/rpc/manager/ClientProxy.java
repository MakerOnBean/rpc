package cloud.makeronbean.rpc.manager;

import cloud.makeronbean.rpc.factory.bundle.Bundle;
import cloud.makeronbean.rpc.factory.bundle.BundleFactory;
import cloud.makeronbean.rpc.message.RpcRequestMessage;
import cloud.makeronbean.rpc.utils.SequenceIdGenerateUtil;
import io.netty.channel.Channel;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author makeronbean
 * @createTime 2023-04-15  09:16
 * @description TODO 生成客户端代理
 */
@SuppressWarnings("all")
public class ClientProxy {

    /**
     * 返回代理对象
     */
    public static <T> T getServiceImpl(Class<T> serviceClass, Channel channel) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new InvocationHandlerImpl(serviceClass, channel)
        );
    }

    static class InvocationHandlerImpl implements InvocationHandler {
        Class<?> serviceClass;
        Channel channel;

        public InvocationHandlerImpl(Class<?> serviceClass, Channel channel) {
            this.serviceClass = serviceClass;
            this.channel = channel;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Integer sequenceId = SequenceIdGenerateUtil.getSequenceId();
            Bundle bundle = BundleFactory.getBundle();
            Promise<Object> promise = new DefaultPromise<>(channel.eventLoop());
            bundle.add(sequenceId, promise);

            RpcRequestMessage rpcRequestMessage = new RpcRequestMessage(
                    sequenceId,
                    serviceClass.getName(),
                    method.getName(),
                    method.getReturnType(),
                    method.getParameterTypes(),
                    args
            );

            // 发送消息
            channel.writeAndFlush(rpcRequestMessage);

            // 等待结果
            promise.await();
            if (promise.isSuccess()) {
                return promise.getNow();
            } else {
                Throwable cause = promise.cause();
                throw new RuntimeException("远程调用失败：", cause);
            }
        }
    }
}
