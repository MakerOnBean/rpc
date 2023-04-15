package cloud.makeronbean.rpc.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author makeronbean
 * @createTime 2023-04-15  10:27
 * @description TODO
 */
@Slf4j
public class HeartBeatServerHandler extends ChannelDuplexHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
        if (idleStateEvent.state() == IdleState.READER_IDLE) {
            log.debug("与{}的连接已断开",ctx.channel());
            ctx.channel().close();
        }
    }
}
