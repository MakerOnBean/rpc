package cloud.makeronbean.test.service.impl;

import cloud.makeronbean.rpc.annotation.RpcService;
import cloud.makeronbean.test.service.HelloService;

/**
 * @author makeronbean
 * @createTime 2023-04-15  11:09
 * @description TODO
 */
@RpcService
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
