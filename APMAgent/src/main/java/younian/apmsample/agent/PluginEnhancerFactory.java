package younian.apmsample.agent;

import younian.apmsample.agent.plugin.TestEnhancer;
import younian.apmsample.agent.plugin.TomcatEnhancer;

import java.util.HashMap;

public class PluginEnhancerFactory {

    public static PluginEnhancer getPluginEnhancerByClass(String className) {
        System.out.println("getPluginEnhancerByClass:" + className);
        HashMap<String, PluginEnhancer> pluginEnhancerHashMap = new HashMap<>();
        pluginEnhancerHashMap.put("org.apache.catalina.core.StandardWrapperValve", new TomcatEnhancer());
        pluginEnhancerHashMap.put("younian.apmsample.agent.test.Test", new TestEnhancer());

        return pluginEnhancerHashMap.get(className);
    }
}
