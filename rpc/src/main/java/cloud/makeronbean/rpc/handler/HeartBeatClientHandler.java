package cloud.makeronbean.rpc.handler;

import cloud.makeronbean.rpc.message.PingMessage;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author makeronbean
 * @createTime 2023-04-15  10:30
 * @description TODO
 */
public class HeartBeatClientHandler extends ChannelDuplexHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
        //if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
            ctx.writeAndFlush(new PingMessage());
        //}
        //super.userEventTriggered(ctx, evt);
    }
}
