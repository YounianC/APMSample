package younian.apmsample.agent.plugin.jdbc;

import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;
import younian.apmsample.agent.plugin.jdbc.sql.WrapConnection;

import java.lang.reflect.Method;
import java.sql.Connection;

public class JdbcInterceptor extends ClassInstanceMethodInterceptor {
    @Override
    protected void before(Method method, Object[] allArguments) {
    }

    @Override
    protected Object after(Object result, Object[] allArguments) {
        if(allArguments == null || allArguments.length < 1 || allArguments[0] == null){
            return result;
        }

        String url = (String) allArguments[0];
        if(result != null){
            Connection realConnection = (Connection) result;
            return new WrapConnection(url, realConnection);
        }
        return result;
    }
}
