package cloud.makeronbean.rpc.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author makeronbean
 * @createTime 2023-04-15  12:26
 * @description TODO
 */
@ConfigurationProperties("rpc")
public class RpcProperties {

    /**
     * 服务器地址
     */
    private String host;

    /**
     * 服务器端口
     */
    private Integer port;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

}
