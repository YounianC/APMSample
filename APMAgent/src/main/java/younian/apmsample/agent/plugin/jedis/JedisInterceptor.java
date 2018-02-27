package younian.apmsample.agent.plugin.jedis;

import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;

public class JedisInterceptor extends ClassInstanceMethodInterceptor {
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
