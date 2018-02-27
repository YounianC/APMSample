package younian.apmsample.agent.plugin.jedis;

import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;

public class JedisInterceptor extends ClassInstanceMethodInterceptor {
    @Override
    protected void before() {
        System.out.println("JedisInterceptor before..");
    }

    @Override
    protected Object after(Object result) {
        System.out.println("JedisInterceptor after..");
        return result;
    }
}
