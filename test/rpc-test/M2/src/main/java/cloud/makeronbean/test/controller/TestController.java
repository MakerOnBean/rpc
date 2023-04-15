package cloud.makeronbean.test.controller;

import cloud.makeronbean.rpc.manager.RpcClientManager;
import cloud.makeronbean.test.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author makeronbean
 * @createTime 2023-04-15  13:02
 * @description TODO
 */
@RestController
public class TestController {

    @Autowired
    private RpcClientManager rpcClientManager;

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name){
        HelloService service = rpcClientManager.getServiceImpl(HelloService.class);
        return service.sayHello(name);
    }
}
