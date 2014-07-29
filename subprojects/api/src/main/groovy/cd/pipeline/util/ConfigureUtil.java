package cd.pipeline.util;

import groovy.lang.Closure;

public class ConfigureUtil {
    private ConfigureUtil() {
        throw new AssertionError("Cannot instantiate this class.");
    }

    public static void configure(Object obj, Closure config) {
        config.setDelegate(obj);
        config.setResolveStrategy(Closure.DELEGATE_FIRST);
        config.call();
    }
}
