package younian.apmsample.agent.plugin.jedis;

import younian.apmsample.agent.context.ContextManager;
import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;

import java.lang.reflect.Method;

public class JedisInterceptor extends ClassInstanceMethodInterceptor {
    protected void before(Method method, Object[] allArguments){
        ContextManager.createSpan("Jedis:" + method.getName());
    }

    @Override
    protected Object after(Object result) {
        ContextManager.stopSpan();
        return result;
    }
}
