package cloud.makeronbean.rpc.auto;

import cloud.makeronbean.rpc.bean.RpcProperties;
import cloud.makeronbean.rpc.manager.RpcClientManager;
import cloud.makeronbean.rpc.manager.RpcServerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author makeronbean
 * @createTime 2023-04-15  12:31
 * @description TODO
 */
@Configuration
@EnableConfigurationProperties(RpcProperties.class)
public class RpcAutoConfiguration {

    @Autowired
    private RpcProperties properties;

    @Bean
    @ConditionalOnMissingBean(RpcClientManager.class)
    @ConditionalOnProperty(prefix = "rpc", name = "client")
    public RpcClientManager rpcClientManager() {
        return new RpcClientManager(properties.getHost(), properties.getPort());
    }

    @Bean
    @ConditionalOnMissingBean(RpcServerManager.class)
    @ConditionalOnProperty(prefix = "rpc", name = "server")
    public RpcServerManager rpcServerManager() {
        return new RpcServerManager(properties.getPort());
    }
}
