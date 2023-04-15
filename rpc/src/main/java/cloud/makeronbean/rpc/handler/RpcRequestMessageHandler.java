package cloud.makeronbean.rpc.handler;

import cloud.makeronbean.rpc.factory.rpcService.RpcServiceContainerFactory;
import cloud.makeronbean.rpc.message.RpcRequestMessage;
import cloud.makeronbean.rpc.message.RpcResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

/**
 * @author makeronbean
 * @createTime 2023-04-14  17:21
 * @description TODO 请求处理
 */
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage msg) throws Exception {
        RpcResponseMessage rpcResponseMessage = new RpcResponseMessage();
        rpcResponseMessage.setSequenceId(msg.getSequenceId());
        try {
            String interfaceName = msg.getInterfaceName();
            String methodName = msg.getMethodName();
            Class<?> returnType = msg.getReturnType();
            Class[] parameterTypes = msg.getParameterTypes();
            Object[] parameterValue = msg.getParameterValue();

            // 反射调用方法
            Object service = RpcServiceContainerFactory.getRpcServiceContainer().getServiceByInterfaceClass(Class.forName(interfaceName));
            Method method = service.getClass().getMethod(methodName, parameterTypes);
            Object result = method.invoke(service, parameterValue);
            if (returnType.isInstance(result)) {
                rpcResponseMessage.setReturnValue(result);
            } else {
                throw new RuntimeException("返回值类型不匹配");
            }
        } catch (Exception e) {
            rpcResponseMessage.setExceptionValue(new Exception(e.getCause().getMessage()));
        }
        ctx.writeAndFlush(rpcResponseMessage);
    }
}
