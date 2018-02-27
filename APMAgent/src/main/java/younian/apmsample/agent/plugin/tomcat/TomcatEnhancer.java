package younian.apmsample.agent.plugin.tomcat;

import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import younian.apmsample.agent.plugin.PluginEnhancer;

import static net.bytebuddy.matcher.ElementMatchers.isStatic;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.not;

public class TomcatEnhancer extends PluginEnhancer {

    public static String ENHANCE_CLASSNAME = "org.apache.catalina.core.StandardWrapperValve";
    public static String INTERCEPTOR_CLASSNAME = "younian.apmsample.agent.plugin.tomcat.TomcatInterceptor";

    @Override
    public String getEnhanceClassName() {
        return ENHANCE_CLASSNAME;
    }

    @Override
    public String getInterceptorClassName() {
        return INTERCEPTOR_CLASSNAME;
    }

    @Override
    public ElementMatcher getInstanceMethodInterceptorMatch() {
        return  named("invoke");
    }
}
