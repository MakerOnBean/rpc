package cloud.makeronbean.test;

import cloud.makeronbean.rpc.annotation.RpcServiceScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author makeronbean
 * @createTime 2023-04-15  12:58
 * @description TODO
 */
@SpringBootApplication
@RpcServiceScan("cloud.makeronbean.test.service")
public class M1Application {
    public static void main(String[] args) {
        SpringApplication.run(M1Application.class, args);
    }
}
