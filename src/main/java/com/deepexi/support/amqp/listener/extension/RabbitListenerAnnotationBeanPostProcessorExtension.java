package com.deepexi.support.amqp.listener.extension;

import com.deepexi.support.amqp.listener.handler.ListenerExtensionHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerAnnotationBeanPostProcessor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author Y.H.Zhou
 * @since 2020/2/4.
 * <p></p>
 */
public class RabbitListenerAnnotationBeanPostProcessorExtension extends RabbitListenerAnnotationBeanPostProcessor {

    private ListenerExtensionHandler listenerExtensionHandler;

    public RabbitListenerAnnotationBeanPostProcessorExtension(ListenerExtensionHandler listenerExtensionHandler) {
        super();
        this.listenerExtensionHandler = listenerExtensionHandler;
    }

    /**
     * 替换 org.springframework.amqp.rabbit.listener.MethodRabbitListenerEndpoint
     * @param rabbitListener
     * @param method
     * @param bean
     * @param beanName
     */
    @Override
    protected void processAmqpListener(RabbitListener rabbitListener, Method method, Object bean, String beanName) {
        Method methodToUse = checkProxy(method, bean);
        MethodRabbitListenerEndpointExtension endpoint = new MethodRabbitListenerEndpointExtension(listenerExtensionHandler);
        endpoint.setMethod(methodToUse);
        processListener(endpoint, rabbitListener, bean, methodToUse, beanName);
    }

    private Method checkProxy(Method method, Object bean) {
        if (AopUtils.isJdkDynamicProxy(bean)) {
            try {
                // Found a @RabbitListener method on the target class for this JDK proxy ->
                // is it also present on the proxy itself?
                method = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                Class<?>[] proxiedInterfaces = ((Advised) bean).getProxiedInterfaces();
                for (Class<?> iface : proxiedInterfaces) {
                    try {
                        method = iface.getMethod(method.getName(), method.getParameterTypes());
                        break;
                    }
                    catch (NoSuchMethodException noMethod) {
                    }
                }
            }
            catch (SecurityException ex) {
                ReflectionUtils.handleReflectionException(ex);
            }
            catch (NoSuchMethodException ex) {
                throw new IllegalStateException(String.format(
                        "@RabbitListener method '%s' found on bean target class '%s', " +
                                "but not found in any interface(s) for bean JDK proxy. Either " +
                                "pull the method up to an interface or switch to subclass (CGLIB) " +
                                "proxies by setting proxy-target-class/proxyTargetClass " +
                                "attribute to 'true'", method.getName(), method.getDeclaringClass().getSimpleName()));
            }
        }
        return method;
    }
}
