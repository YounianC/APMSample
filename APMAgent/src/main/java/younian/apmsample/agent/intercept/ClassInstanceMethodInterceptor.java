package younian.apmsample.agent.intercept;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ClassInstanceMethodInterceptor {
    @RuntimeType
    public Object intercept(@This Object obj, @AllArguments Object[] allArguments, @Origin Method method,
                            @SuperCall Callable<?> zuper) throws Throwable {

        System.out.println("intercept " + method.getName());
        Object result = null;
        try {
            result = zuper.call();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;

    }
}
