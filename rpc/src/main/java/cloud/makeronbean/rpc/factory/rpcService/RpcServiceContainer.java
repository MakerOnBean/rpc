package cloud.makeronbean.rpc.factory.rpcService;

/**
 * @author makeronbean
 * @createTime 2023-04-14  19:24
 * @description TODO
 */
public interface RpcServiceContainer {

    /**
     * 添加一个被注解标识的类到容器中
     */
    void addService(Object obj);

    /**
     * 通过接口类对象 获取容器中的实现类
     */
    <T> T getServiceByInterfaceClass(Class<T> clazz);
}
