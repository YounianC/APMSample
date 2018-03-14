package younian.apmsample.agent.plugin.dubbo;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import younian.apmsample.agent.context.ContextManager;
import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;

import java.lang.reflect.Method;

public class DubboInterceptor extends ClassInstanceMethodInterceptor {

    private String generateOperateName(URL requestURL, Invocation invocation) {

        StringBuilder operateName = new StringBuilder();
        operateName.append(requestURL.getPath());
        operateName.append(".").append(invocation.getMethodName()).append("(");
        Class<?>[] parameterTypes = invocation.getParameterTypes();
        if (parameterTypes != null && parameterTypes.length > 0) {
            for (int i = 0; i < parameterTypes.length; i++) {
                operateName.append(parameterTypes[i].getSimpleName());
                if (i < parameterTypes.length - 1) {
                    operateName.append("").append(",");
                }
            }
        }
        operateName.append(")");
        return operateName.toString();
    }

    @Override
    protected void before(Method method, Object[] allArguments) {
        Invoker<?> invoker = (Invoker<?>) allArguments[0];
        Invocation invocation = (Invocation) allArguments[1];
        URL requestURL = invoker.getUrl();
        ContextManager.createSpan("Dubbo:" + generateOperateName(requestURL, invocation));
    }

    @Override
    protected Object after(Object result, Object[] allArguments) {
        ContextManager.stopSpan();
        return result;
    }
}
