package younian.apmsample.agent.plugin;

import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;

import static net.bytebuddy.matcher.ElementMatchers.isStatic;
import static net.bytebuddy.matcher.ElementMatchers.not;

public abstract class PluginEnhancer {
    public abstract String getEnhanceClassName();
    public abstract String getInterceptorClassName();
    public abstract ElementMatcher getInstanceMethodInterceptorMatch();

    public DynamicType.Builder<?> doIntercept(DynamicType.Builder<?> builder){
        try {
            ClassInstanceMethodInterceptor classInstanceMethodInterceptor =(ClassInstanceMethodInterceptor) Class.forName(getInterceptorClassName()).newInstance();
            builder = builder.method(not(isStatic()).and(getInstanceMethodInterceptorMatch())).intercept(MethodDelegation.to(classInstanceMethodInterceptor));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return builder;
    }
}
