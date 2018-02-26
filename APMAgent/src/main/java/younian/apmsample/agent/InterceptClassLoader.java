package younian.apmsample.agent;

import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class InterceptClassLoader {

    public static ElementMatcher getMatcher(){
        return named("org.apache.catalina.core.StandardWrapperValve").or(named("younian.apmsample.agent.test.Test"));
    }
}
