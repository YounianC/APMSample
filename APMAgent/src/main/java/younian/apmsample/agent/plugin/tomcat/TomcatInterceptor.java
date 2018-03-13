package younian.apmsample.agent.plugin.tomcat;

import younian.apmsample.agent.context.ContextManager;
import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class TomcatInterceptor extends ClassInstanceMethodInterceptor{
    protected void before(Method method, Object[] allArguments){
        HttpServletRequest request = (HttpServletRequest) allArguments[0];
        ContextManager.createSpan("Tomcat:" + request.getRequestURI());
    }

    @Override
    protected Object after(Object result, Object[] allArguments) {
        ContextManager.stopSpan();
        return result;
    }
}
