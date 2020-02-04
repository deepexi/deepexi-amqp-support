package com.deepexi.support.amqp.listener.extension;

import org.springframework.amqp.rabbit.config.RabbitListenerConfigUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Objects;

/**
 * @author Y.H.Zhou
 * @since 2020/2/4.
 * <p></p>
 */
public class RabbitConfigExtensionBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    /**
     * 替换 RabbitListenerAnnotationBeanPostProcessor
     * org.springframework.amqp.rabbit.annotation.RabbitBootstrapConfiguration#rabbitListenerAnnotationProcessor()
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition(RabbitListenerConfigUtils.RABBIT_LISTENER_ANNOTATION_PROCESSOR_BEAN_NAME);

        if (Objects.isNull(beanDefinition)) {
            return;
        }

        beanDefinition.setFactoryBeanName("amqpSupportConfig");
        beanDefinition.setFactoryMethodName("getRabbitListenerAnnotationBeanPostProcessor");
    }
}
