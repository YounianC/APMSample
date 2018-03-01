package younian.apmsample.agent.plugin.test;

import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;
import younian.apmsample.agent.plugin.PluginEnhancer;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class TestEnhancer extends PluginEnhancer {

    public static String ENHANCE_CLASSNAME = "Controller.IndexController";
    public static String INTERCEPTOR_CLASSNAME = "younian.apmsample.agent.plugin.test.TestInterceptor";

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
        return named("test1").or(named("test2")).or(named("testStatic"));
    }
}
