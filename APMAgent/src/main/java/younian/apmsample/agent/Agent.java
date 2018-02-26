package younian.apmsample.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.utility.JavaModule;
import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class Agent {
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("Enter premain.....");
        new AgentBuilder.Default().type(named("org.apache.catalina.core.StandardWrapperValve")).transform(new AgentBuilder.Transformer() {
            @Override
            public Builder<?> transform(Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader) {
                System.out.println("transform...");

                ClassInstanceMethodInterceptor methodInterceptor = new ClassInstanceMethodInterceptor();
                ElementMatcher matcher = named("invoke");
                builder = builder.method(not(isStatic()).and(matcher)).intercept(MethodDelegation.to(methodInterceptor));

                return builder;
            }
        }).with(new AgentBuilder.Listener() {
            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
                                         DynamicType dynamicType) {
            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
            }

            @Override
            public void onError(String typeName, ClassLoader classLoader, JavaModule module, Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete(String typeName, ClassLoader classLoader, JavaModule module) {
            }
        }).installOn(instrumentation);
    }
}
