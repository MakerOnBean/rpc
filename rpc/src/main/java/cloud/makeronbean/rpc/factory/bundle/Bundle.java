package cloud.makeronbean.rpc.factory.bundle;

import io.netty.util.concurrent.Promise;

/**
 * @author makeronbean
 * @createTime 2023-04-15  09:07
 * @description TODO 数据传送包裹的容器
 */
public interface Bundle {

    /**
     * 向容器中添加一个包裹
     */
    void add(Integer sequenceId, Promise<Object> promise);

    /**
     * 通过 sequenceId 从容器中获取包裹，并移除容器
     */
    Promise<Object> getAndRemove(Integer sequenceId);



}
