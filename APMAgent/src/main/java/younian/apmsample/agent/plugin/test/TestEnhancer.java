package younian.apmsample.agent.plugin.test;

import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import younian.apmsample.agent.plugin.PluginEnhancer;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class TestEnhancer implements PluginEnhancer {

    public static String ENHANCE_CLASSNAME = "younian.apmsample.agent.test.Test";

    @Override
    public String getEnhanceClassName() {
        return ENHANCE_CLASSNAME;
    }

    public DynamicType.Builder<?> doIntercept(DynamicType.Builder<?> builder){
        ElementMatcher matcher = named("test1").or(named("test2")).or(named("testStatic"));
        builder = builder.method(not(isStatic()).and(matcher)).intercept(MethodDelegation.to(new TestInterceptor()));
        System.out.println("TestEnhancer doIntercept");
        return builder;
    }
}
