package cloud.makeronbean.rpc.protocol;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @author makeronbean
 * @createTime 2023-04-14  16:33
 * @description TODO GSON 需要用到的序列化适配器
 */
class GsonClassCodec implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>> {

    /**
     * 反序列化 class 对象
     */
    @Override
    public Class<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            return Class.forName(jsonElement.getAsString());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("序列化的 Class 对象不存在", e);
        }
    }


    /**
     * 序列化 class对象
     */
    @Override
    public JsonElement serialize(Class<?> aClass, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(aClass.getName());
    }
}
