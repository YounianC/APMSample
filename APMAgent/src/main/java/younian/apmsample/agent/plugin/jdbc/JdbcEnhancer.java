package younian.apmsample.agent.plugin.jdbc;

import net.bytebuddy.matcher.ElementMatcher;
import younian.apmsample.agent.plugin.PluginEnhancer;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class JdbcEnhancer extends PluginEnhancer {

    public static String ENHANCE_CLASSNAME = "com.mysql.jdbc.Driver";
    public static String INTERCEPTOR_CLASSNAME = "younian.apmsample.agent.plugin.jdbc.JdbcInterceptor";

    @Override
    public String getEnhanceClassName() {
        return ENHANCE_CLASSNAME;
    }

    @Override
    public String getInterceptorClassName() {
        return INTERCEPTOR_CLASSNAME;
    }

    @Override
    public ElementMatcher getInstanceMethodInterceptorMatch() {
        return named("connect");
    }
}
