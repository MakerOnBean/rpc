package cloud.makeronbean.rpc.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author makeronbean
 * @createTime 2023-04-15  09:25
 * @description TODO
 */
public class SequenceIdGenerateUtil {
    private static AtomicInteger integer = new AtomicInteger(0);

    public static Integer getSequenceId() {
        return integer.incrementAndGet();
    }
}
