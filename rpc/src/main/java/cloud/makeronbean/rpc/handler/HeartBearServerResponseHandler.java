package cloud.makeronbean.rpc.handler;

import cloud.makeronbean.rpc.message.PingMessage;
import cloud.makeronbean.rpc.message.PongMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author makeronbean
 * @createTime 2023-04-15  11:18
 * @description TODO
 */
@ChannelHandler.Sharable
public class HeartBearServerResponseHandler extends SimpleChannelInboundHandler<PingMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PingMessage msg) throws Exception {
        PongMessage pongMessage = new PongMessage();
        pongMessage.setSequenceId(msg.getSequenceId());
        ctx.fireChannelRead(pongMessage);
    }
}
