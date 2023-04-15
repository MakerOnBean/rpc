package cloud.makeronbean.rpc.factory.rpcService;

/**
 * @author makeronbean
 * @createTime 2023-04-14  19:27
 * @description TODO 简单工厂
 */
public abstract class RpcServiceContainerFactory {
    private static final RpcServiceContainer CONTAINER = new RpcServiceContainerScanImpl();

    public static RpcServiceContainer getRpcServiceContainer() {
        return CONTAINER;
    }
}
