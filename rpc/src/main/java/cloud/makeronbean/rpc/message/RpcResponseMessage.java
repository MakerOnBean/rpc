package cloud.makeronbean.rpc.message;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author makeronbean
 */
@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class RpcResponseMessage extends Message {
    /**
     * 返回值
     */
    private Object returnValue;
    /**
     * 异常值
     */
    private Exception exceptionValue;

    @Override
    public int getMessageType() {
        return RPC_MESSAGE_TYPE_RESPONSE;
    }
}