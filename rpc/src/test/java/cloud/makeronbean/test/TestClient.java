package cloud.makeronbean.test;

import cloud.makeronbean.rpc.manager.RpcClientManager;

/**
 * @author makeronbean
 * @createTime 2023-04-15  11:10
 * @description TODO
 */
public class TestClient {
    public static void main(String[] args) {
        RpcClientManager instance = RpcClientManager.getInstance();
        //HelloService service = instance.getServiceImpl(HelloService.class);
        //System.out.println(service.sayHello("dk"));
    }
}
