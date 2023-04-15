package cloud.makeronbean.rpc.factory.bundle;

/**
 * @author makeronbean
 * @createTime 2023-04-15  09:12
 * @description TODO
 */
public class BundleFactory {
    private static final Bundle BUNDLE = new BundleMapImpl();

    public static Bundle getBundle() {
        return BUNDLE;
    }
}
