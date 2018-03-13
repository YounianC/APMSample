package younian.apmsample.agent.plugin;

import net.bytebuddy.matcher.ElementMatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.none;

public class PluginEnhancerFactory {

    private final static String PLUGIN_FILE_NAME = "plugin.def";

    public final static Map<String, PluginEnhancer> pluginEnhancerHashMap = new HashMap<>();
    static {
        try {
            //找出配置文件路径
            Enumeration<URL> urls = PluginEnhancerFactory.class.getClassLoader().getResources(PLUGIN_FILE_NAME);
            List<URL> cfgUrlPaths = new ArrayList<URL>();
            while (urls.hasMoreElements()) {
                cfgUrlPaths.add(urls.nextElement());
            }

            //读取所有配置文件内容
            List<String> classList = new ArrayList<>();
            for (URL url : cfgUrlPaths) {
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(url.openStream()));
                    String classname = null;
                    while ((classname = in.readLine()) != null) {
                        classList.add(classname);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
            System.out.println("Enhancer List:" + classList);

            //初始化pluginEnhancerHashMap
            for (String classFullname : classList) {
                try {
                    Class<?> clazz = Class.forName(classFullname);
                    PluginEnhancer instance = (PluginEnhancer) clazz.newInstance();
                    pluginEnhancerHashMap.put(instance.getEnhanceClassName(), instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PluginEnhancer getPluginEnhancerByClass(String className) {
        System.out.println("Enhance getPluginEnhancerByClass:" + className);
        return pluginEnhancerHashMap.get(className);
    }

    public static ElementMatcher getMatcher(){
        Iterator<PluginEnhancer> iter = pluginEnhancerHashMap.values().iterator();
        ElementMatcher.Junction matcher = none();
        while (iter.hasNext()) {
            matcher = matcher.or(named( iter.next().getEnhanceClassName()));
        }
        return matcher;
    }

}
