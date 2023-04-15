package cloud.makeronbean.rpc.factory.bundle;

import io.netty.util.concurrent.Promise;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author makeronbean
 * @createTime 2023-04-15  09:10
 * @description TODO
 */
public class BundleMapImpl implements Bundle{

    private final Map<Integer, Promise<Object>> bundleMap = new ConcurrentHashMap<>();

    @Override
    public void add(Integer sequenceId, Promise<Object> promise) {
        bundleMap.put(sequenceId, promise);
    }

    @Override
    public Promise<Object> getAndRemove(Integer sequenceId) {
        return bundleMap.remove(sequenceId);
    }
}
