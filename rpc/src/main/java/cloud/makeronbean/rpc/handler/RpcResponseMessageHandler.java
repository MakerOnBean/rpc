package cloud.makeronbean.rpc.handler;

import cloud.makeronbean.rpc.factory.bundle.Bundle;
import cloud.makeronbean.rpc.factory.bundle.BundleFactory;
import cloud.makeronbean.rpc.message.RpcResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;

/**
 * @author makeronbean
 * @createTime 2023-04-15  09:04
 * @description TODO 响应处理
 */
@ChannelHandler.Sharable
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage msg) throws Exception {
        Bundle bundle = BundleFactory.getBundle();
        Promise<Object> promise = bundle.getAndRemove(msg.getSequenceId());
        Exception exceptionValue = msg.getExceptionValue();
        if (exceptionValue != null) {
            promise.setFailure(exceptionValue);
            return;
        }
        Object returnValue = msg.getReturnValue();
        promise.setSuccess(returnValue);
    }
}
