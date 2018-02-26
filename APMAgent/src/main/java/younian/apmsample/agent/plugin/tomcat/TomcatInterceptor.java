package younian.apmsample.agent.plugin.tomcat;

import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;

public class TomcatInterceptor extends ClassInstanceMethodInterceptor{
    protected void before(){
        System.out.println("TomcatInterceptor before...");
    }

    @Override
    protected Object after(Object result) {
        System.out.println("TomcatInterceptor after...");
        return result;
    }
}
