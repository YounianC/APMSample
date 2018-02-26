package younian.apmsample.agent.plugin.test;

import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;

public class TestInterceptor extends ClassInstanceMethodInterceptor {
    @Override
    protected void before() {
        System.out.println("TestInterceptor before..");
    }

    @Override
    protected Object after(Object result) {
        System.out.println("TestInterceptor after..");
        return result;
    }
}
