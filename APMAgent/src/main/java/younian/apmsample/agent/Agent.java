package younian.apmsample.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.utility.JavaModule;
import java.lang.instrument.Instrumentation;


public class Agent {
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("Enter premain.....");
        new AgentBuilder.Default().type(InterceptClassLoader.getMatcher()).transform(new AgentBuilder.Transformer() {
            @Override
            public Builder<?> transform(Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader) {
                System.out.println("transform...");

                PluginEnhancer pluginEnhancer = new PluginEnhancer(typeDescription.getTypeName());
                builder = pluginEnhancer.doIntercept(builder);

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
