package younian.apmsample.agent.plugin;

import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatcher;

public interface PluginEnhancer {
    public String getEnhanceClassName();
    public ElementMatcher getInstanceMethodInterceptorMatch();
    public DynamicType.Builder<?> doIntercept(DynamicType.Builder<?> builder);
}
