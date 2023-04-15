package cloud.makeronbean.rpc.protocol;

import com.google.gson.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author makeronbean
 * @createTime 2023-04-14  16:21
 * @description TODO 协议对象序列化接口
 */
@SuppressWarnings("all")
public interface Serializer {
    /**
     * 反序列化
     */
    <T> T deserializer(Class<T> clazz, byte[] bytes);

    /**
     * 序列化
     */
    <T> byte[] serializer(T obj);

    enum Algorithm implements Serializer {
        JDK {
            @Override
            public <T> T deserializer(Class<T> clazz, byte[] bytes) {
                try {
                    return (T) new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException("JDK 反序列化失败", e);
                }
            }

            @Override
            public <T> byte[] serializer(T obj) {
                try {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    new ObjectOutputStream(out).writeObject(obj);
                    return out.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException("JDK 序列化异常", e);
                }
            }
        },

        JSON {
            Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new GsonClassCodec()).create();

            @Override
            public <T> T deserializer(Class<T> clazz, byte[] bytes) {
                try {
                    String string = new String(bytes, StandardCharsets.UTF_8);
                    return gson.fromJson(string, clazz);
                } catch (JsonSyntaxException e) {
                    throw new RuntimeException("JSON 反序列化失败", e);
                }
            }

            @Override
            public <T> byte[] serializer(T obj) {
                try {
                    return gson.toJson(obj).getBytes(StandardCharsets.UTF_8);
                } catch (Exception e) {
                    throw new RuntimeException("JSON 序列化失败", e);
                }
            }
        }
    }
}
