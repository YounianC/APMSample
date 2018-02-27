package younian.apmsample.agent.plugin.test;

import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;

import java.lang.reflect.Method;

public class TestInterceptor extends ClassInstanceMethodInterceptor {
    @Override
    protected void before(Method method) {
        System.out.println("TestInterceptor before..");
    }

    @Override
    protected Object after(Object result) {
        System.out.println("TestInterceptor after..");
        return result;
    }
}
