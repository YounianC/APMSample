package younian.apmsample.agent.plugin;

import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import younian.apmsample.agent.intercept.ClassInstanceMethodInterceptor;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static net.bytebuddy.matcher.ElementMatchers.isStatic;
import static net.bytebuddy.matcher.ElementMatchers.not;

public abstract class PluginEnhancer {
    public abstract String getEnhanceClassName();
    public abstract String getInterceptorClassName();
    public abstract ElementMatcher getInstanceMethodInterceptorMatch();

    public DynamicType.Builder<?> doIntercept(DynamicType.Builder<?> builder, ClassLoader classLoader){
        try {
            ClassInstanceMethodInterceptor classInstanceMethodInterceptor = null;
            if (PluginEnhancer.class.getClassLoader().equals(classLoader)) {
                classInstanceMethodInterceptor = (ClassInstanceMethodInterceptor)  classLoader.loadClass(getInterceptorClassName()).newInstance();
            } else {
                try {
                    classInstanceMethodInterceptor = findLoadedClass(getInterceptorClassName(), classLoader);
                    if (classInstanceMethodInterceptor == null) {
                        classInstanceMethodInterceptor = loadBinaryClass(getInterceptorClassName(), classLoader);
                    }
                    if (classInstanceMethodInterceptor == null) {
                        throw new ClassNotFoundException(classLoader.toString() + " load interceptor class:" + getInterceptorClassName() + " failure.");
                    }
                }catch(Exception e){
                    throw new ClassNotFoundException(classLoader.toString() + " load interceptor class:" + getInterceptorClassName() + " failure.", e);
                } finally {
                }
            }
            //ClassInstanceMethodInterceptor classInstanceMethodInterceptor =(ClassInstanceMethodInterceptor) classLoader.loadClass(getInterceptorClassName()).newInstance();
            builder = builder.method(not(isStatic()).and(getInstanceMethodInterceptorMatch())).intercept(MethodDelegation.to(classInstanceMethodInterceptor));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return builder;
    }

    private static <T> T loadBinaryClass(String interceptorCalssname, ClassLoader classloader) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        String path = "/" + interceptorCalssname.replace('.', '/').concat(".class");
        byte[] data = null;
        BufferedInputStream is = null;
        ByteArrayOutputStream out = null;
        try {
            is = new BufferedInputStream(PluginEnhancer.class.getResourceAsStream(path));
            out = new ByteArrayOutputStream();
            int ch = 0;
            while((ch = is.read()) != -1){
                out.write(ch);
            }
            data = out.toByteArray();
        } catch (IOException e) {

        }finally{
            if(is != null){
                try {
                    is.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if(out != null){
                try {
                    out.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        Method defineClassMethod = null;
        Class<?> classloadClazz = classloader.getClass();
        while(defineClassMethod == null && classloadClazz != null){
            try {
                defineClassMethod = classloadClazz.getDeclaredMethod("defineClass",String.class,byte[].class,int.class,int.class);
            } catch (NoSuchMethodException e) {
                classloadClazz = classloadClazz.getSuperclass();
            }
        }
        defineClassMethod.setAccessible(true);

        defineClassMethod.setAccessible(true);
        System.out.println("load binary code of " + interceptorCalssname + " to classloader " + classloader);
        Class<?> type = (Class<?>) defineClassMethod.invoke(classloader, interceptorCalssname, data, 0, data.length);
        return (T) type.newInstance();
    }

    private static <T> T findLoadedClass(String interceptorCalssname, ClassLoader classloader)

            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException {
        Class<?> classloadClazz = classloader.getClass();

        Method findLoadedClassMethod = null;
        while (findLoadedClassMethod == null && classloadClazz != null) {
            try {
                findLoadedClassMethod = classloadClazz.getDeclaredMethod("findLoadedClass", String.class);
            } catch (NoSuchMethodException e) {
                classloadClazz = classloadClazz.getSuperclass();
            }
        }
        findLoadedClassMethod.setAccessible(true);

        Class<?> interceptorClazz = null;
        try {
            interceptorClazz = (Class<?>) findLoadedClassMethod.invoke(classloader, interceptorCalssname);
        } catch (Exception e) {
            return null;
        }

        if (interceptorClazz == null) {
            return null;
        }
        return (T) interceptorClazz.newInstance();
    }
}
