package younian.apmsample.agent.plugin.jedis;

import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import younian.apmsample.agent.plugin.PluginEnhancer;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class JedisEnhancer implements PluginEnhancer {

    public static String ENHANCE_CLASSNAME = "redis.clients.jedis.Jedis";

    @Override
    public String getEnhanceClassName() {
        return ENHANCE_CLASSNAME;
    }

    @Override
    public ElementMatcher getInstanceMethodInterceptorMatch() {
        return named("test1").or(named("test2")).or(named("testStatic"));
    }

    public DynamicType.Builder<?> doIntercept(DynamicType.Builder<?> builder){
        builder = builder.method(not(isStatic()).and(getInstanceMethodInterceptorMatch())).intercept(MethodDelegation.to(new JedisInterceptor()));
        System.out.println("TestEnhancer doIntercept");
        return builder;
    }
}
