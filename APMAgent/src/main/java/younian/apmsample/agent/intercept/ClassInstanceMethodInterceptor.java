package younian.apmsample.agent.intercept;

import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatcher;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ClassInstanceMethodInterceptor {
    @RuntimeType
    public Object intercept(@This Object obj, @AllArguments Object[] allArguments, @Origin Method method,
                                         @SuperCall Callable<?> zuper) throws Throwable {
        long start = System.currentTimeMillis();
        HttpServletRequest request = (HttpServletRequest) allArguments[0];

        Object result = null;
        try {
            result = zuper.call();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("intercept " + method.getName() + " ,user requested url:" + request.getRequestURI() + " ,costs:" + (end - start));

        return result;
    }
}
