package younian.apmsample.agent;

import net.bytebuddy.dynamic.DynamicType;

public interface PluginEnhancer {
    public DynamicType.Builder<?> doIntercept(DynamicType.Builder<?> builder);
}
