package younian.apmsample.agent.intercept;

import net.bytebuddy.implementation.bind.annotation.*;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public interface ClassInstanceMethodInterceptor {
    @RuntimeType
    Object intercept(@This Object obj, @AllArguments Object[] allArguments, @Origin Method method, @SuperCall Callable<?> zuper) throws Throwable;
}
