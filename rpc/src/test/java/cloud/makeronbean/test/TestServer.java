package cloud.makeronbean.test;

import cloud.makeronbean.rpc.annotation.RpcServiceScan;
import cloud.makeronbean.rpc.manager.RpcServerManager;

/**
 * @author makeronbean
 * @createTime 2023-04-15  11:08
 * @description TODO
 */
@RpcServiceScan("cloud.makeronbean.test.service")
public class TestServer {
    public static void main(String[] args) {
        new RpcServerManager();
    }
}
