package younian.apmsample.agent.plugin.tomcat;

import younian.apmsample.agent.context.ContextManager;
import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;

import java.lang.reflect.Method;

public class TomcatInterceptor extends ClassInstanceMethodInterceptor{
    protected void before(Method method){
        ContextManager.createSpan(method.getName());
    }

    @Override
    protected Object after(Object result) {
        ContextManager.stopSpan();
        return result;
    }
}
