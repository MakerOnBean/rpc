package cloud.makeronbean.rpc.message;

public class PongMessage extends Message {
    @Override
    public int getMessageType() {
        return PongMessage;
    }
}
