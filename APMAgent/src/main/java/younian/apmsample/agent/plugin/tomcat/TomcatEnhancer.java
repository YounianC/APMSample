package younian.apmsample.agent.plugin.tomcat;

import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import younian.apmsample.agent.plugin.PluginEnhancer;

import static net.bytebuddy.matcher.ElementMatchers.isStatic;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.not;

public class TomcatEnhancer implements PluginEnhancer {

    public static String ENHANCE_CLASSNAME = "org.apache.catalina.core.StandardWrapperValve";

    @Override
    public String getEnhanceClassName() {
        return ENHANCE_CLASSNAME;
    }

    @Override
    public ElementMatcher getInstanceMethodInterceptorMatch() {
        return  named("invoke");
    }

    public DynamicType.Builder<?> doIntercept(DynamicType.Builder<?> builder){
        builder = builder.method(not(isStatic()).and(getInstanceMethodInterceptorMatch())).intercept(MethodDelegation.to(new TomcatInterceptor()));
        System.out.println("TomcatEnhancer doIntercept");
        return builder;
    }
}
