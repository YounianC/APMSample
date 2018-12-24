package younian.apmsample.agent.plugin.dubbo;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcContext;
import younian.apmsample.agent.context.ContextCarrier;
import younian.apmsample.agent.context.ContextManager;
import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;

import java.lang.reflect.Method;

public class DubboInterceptor extends ClassInstanceMethodInterceptor {

    private final static String DUBBO_ATTACHEMENT_CONTEXTCARRIER = "CONTEXTCARRIER";

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

    public ContextCarrier extractContextCarrier() {
        RpcContext rpcContext = RpcContext.getContext();
        boolean isConsumer = rpcContext.isConsumerSide();
        ContextCarrier carrier = null;
        if (!isConsumer) {// provider
            carrier = new ContextCarrier().deserialize(rpcContext.getAttachments().get(DUBBO_ATTACHEMENT_CONTEXTCARRIER));
        }
        return carrier;
    }

    @Override
    protected void before(Method method, Object[] allArguments) {
        Invoker<?> invoker = (Invoker<?>) allArguments[0];
        Invocation invocation = (Invocation) allArguments[1];
        URL requestURL = invoker.getUrl();

        ContextCarrier contextCarrier = extractContextCarrier();
        ContextManager.createSpan(contextCarrier, "Dubbo", generateOperateName(requestURL, invocation));

        RpcContext rpcContext = RpcContext.getContext();
        boolean isConsumer = rpcContext.isConsumerSide();
        if (isConsumer) {
            ContextCarrier newContextCarrier = new ContextCarrier();
            ContextManager.inject(newContextCarrier);
            rpcContext.getAttachments().put(DUBBO_ATTACHEMENT_CONTEXTCARRIER, newContextCarrier.serialize());
        }
    }

    @Override
    protected Object after(Object result, Object[] allArguments) {
        ContextManager.stopSpan();
        return result;
    }
}
