package younian.apmsample.agent.intercept;

import net.bytebuddy.implementation.bind.annotation.*;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public abstract class ClassInstanceMethodInterceptor {

    protected abstract void before(Method method, Object[] allArguments);
    protected abstract Object after(Object result, Object[] allArguments);

    @RuntimeType
    public Object intercept(@This Object obj, @AllArguments Object[] allArguments, @Origin Method method, @SuperCall Callable<?> zuper) throws Throwable {
        try {
            before(method, allArguments);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        Object result = null;
        try {
            result = zuper.call();
        } catch (Throwable t) {
            throw t;
        } finally {
            try {
                result = after(result, allArguments);
            } catch (Throwable e){
                e.printStackTrace();
            }
        }
        return result;
    }
}
