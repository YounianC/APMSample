package younian.apmsample.agent.plugin;

import net.bytebuddy.dynamic.DynamicType;

public interface PluginEnhancer {
    public String getEnhanceClassName();
    public DynamicType.Builder<?> doIntercept(DynamicType.Builder<?> builder);
}
