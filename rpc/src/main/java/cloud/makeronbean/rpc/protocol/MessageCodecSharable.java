package cloud.makeronbean.rpc.protocol;

import cloud.makeronbean.rpc.config.RpcConfig;
import cloud.makeronbean.rpc.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @author makeronbean
 * @createTime 2023-04-14  16:18
 * @description TODO
 */
@Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {

    /**
     * 编码协议
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> outList) throws Exception {
        ByteBuf out = ctx.alloc().buffer();
        // 字节数组
        byte[] bytes = RpcConfig.getSerializerAlgorithm().serializer(msg);
        // 4 魔数
        out.writeBytes(new byte[]{0,5,2,7});
        // 1 协议版本
        out.writeByte(1);
        // 1 序列化方式
        out.writeByte(RpcConfig.getSerializerAlgorithm().ordinal());
        // 1 指令类型
        out.writeByte(msg.getMessageType());
        // 4 请求序号
        out.writeInt(msg.getSequenceId());
        // 1 对齐（无意义）
        out.writeByte(0xff);
        // 4 内容长度
        out.writeInt(bytes.length);
        // 写入内容
        out.writeBytes(bytes);
        outList.add(out);
    }


    /**
     * 解析协议
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        // 4 魔数
        int magicNum = msg.readInt();
        // 1 协议版本
        byte version = msg.readByte();
        // 1 序列化方式
        byte serializerAlgorithm = msg.readByte();
        // 1 指令类型
        byte messageType = msg.readByte();
        // 4 请求序号
        int sequenceId = msg.readInt();
        // 1 无意义
        msg.readByte();
        // 4 内容长度
        int length = msg.readInt();
        // 反序列化
        byte[] bytes = new byte[length];
        msg.readBytes(bytes, 0, length);
        Class<? extends Message> messageClass = Message.getMessageClass(messageType);
        Message message = Serializer.Algorithm.values()[serializerAlgorithm].deserializer(messageClass, bytes);

        // 解析完成 传递给下一个handler
        out.add(message);
    }
}
