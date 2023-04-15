package cloud.makeronbean.rpc.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author makeronbean
 * @createTime 2023-04-14  16:52
 * @description TODO 处理黏包、半包
 */
public class ProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {
    public ProtocolFrameDecoder() {
        super(1024, 12, 4, 0, 0);
    }

    public ProtocolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

}
