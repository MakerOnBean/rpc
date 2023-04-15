package cloud.makeronbean.rpc.config;

import cloud.makeronbean.rpc.protocol.Serializer;

import java.util.ResourceBundle;

/**
 * @author makeronbean
 * @createTime 2023-04-14  16:20
 * @description TODO
 */
public class RpcConfig {

    private static final String ALGORITHM = "Serializer.Algorithm";
    private static final ResourceBundle BUNDLE;

    static {
        BUNDLE = ResourceBundle.getBundle("rpcConfig");
    }


    /**
     * 获取序列化算法
     */
    public static Serializer.Algorithm getSerializerAlgorithm() {
        String value = BUNDLE.getString(ALGORITHM);
        if (value == null) {
            // 默认提供JDK实现
            return Serializer.Algorithm.JDK;
        } else {
            return Serializer.Algorithm.valueOf(value);
        }
    }

    /**
     * 根据key获取值
     */
    public static String get(String key) {
        return BUNDLE.getString(key);
    }
}
