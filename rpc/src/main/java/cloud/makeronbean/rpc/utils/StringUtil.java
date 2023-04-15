package cloud.makeronbean.rpc.utils;

/**
 * @author makeronbean
 * @createTime 2023-04-14  19:09
 * @description TODO
 */
public class StringUtil {
    public static boolean isEmpty(String... strings) {
        for (String string : strings) {
            if (null == string || "".equals(string)) {
                return true;
            }
        }
        return false;
    }
}
