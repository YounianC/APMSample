package younian.apmsample.agent.plugin.test;

import net.bytebuddy.implementation.bind.annotation.*;
import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class TestInterceptor implements ClassInstanceMethodInterceptor {
    @Override
    @RuntimeType
    public Object intercept(@This Object obj, @AllArguments Object[] allArguments, @Origin Method method, @SuperCall Callable<?> zuper) throws Throwable {
        Object result = null;
        try {
            result = zuper.call();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.out.println("intercept " + method.getName());

        return result;
    }
}
