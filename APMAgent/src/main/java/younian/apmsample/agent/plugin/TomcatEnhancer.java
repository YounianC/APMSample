package younian.apmsample.agent.plugin;

import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import younian.apmsample.agent.PluginEnhancer;
import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.isStatic;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.not;

public class TomcatEnhancer implements PluginEnhancer {

    public DynamicType.Builder<?> doIntercept(DynamicType.Builder<?> builder){
        ElementMatcher matcher = named("invoke");
        builder = builder.method(not(isStatic()).and(matcher)).intercept(MethodDelegation.to(new ClassInstanceMethodInterceptor()));

        return builder;
    }
}
