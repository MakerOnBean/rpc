package cloud.makeronbean.rpc.factory.rpcService;

import java.util.HashSet;
import java.util.Set;

/**
 * @author makeronbean
 * @createTime 2023-04-14  19:35
 * @description TODO 注解扫描实现
 */
public class RpcServiceContainerScanImpl implements RpcServiceContainer{

    private final Set<Object> serviceSet = new HashSet<>();
    @Override
    public void addService(Object obj) {
        serviceSet.add(obj);
    }

    @Override
    public <T> T getServiceByInterfaceClass(Class<T> clazz) {
        for (Object o : serviceSet) {
            if (clazz.isInstance(o)) {
                return (T) o;
            }
        }
        throw new RuntimeException("容器中没有找到指定的服务实现类");
    }
}
