package cloud.makeronbean.test.service.impl;

import cloud.makeronbean.rpc.annotation.RpcService;
import cloud.makeronbean.test.service.HelloService;

/**
 * @author makeronbean
 * @createTime 2023-04-15  12:59
 * @description TODO
 */
@RpcService
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
