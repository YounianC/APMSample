package younian.apmsample.agent.plugin.dubbo;

import net.bytebuddy.matcher.ElementMatcher;
import younian.apmsample.agent.plugin.PluginEnhancer;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class DubboEnhancer extends PluginEnhancer {

	private final static String ENHANCE_CLASSNAME = "com.alibaba.dubbo.monitor.support.MonitorFilter";
	private final static String INTERCEPTOR_CLASSNAME = "younian.apmsample.agent.plugin.dubbo.DubboInterceptor";

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
		return named("invoke");
	}
}
