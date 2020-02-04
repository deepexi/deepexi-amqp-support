package com.deepexi.support.amqp.listener.aspectJ;

import com.deepexi.support.amqp.listener.handler.ListenerExtensionHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Y.H.Zhou
 * @since 2020/2/4.
 * <p></p>
 */
@Component
@Aspect
public class ListenerAspect {

    @Autowired
    public ListenerExtensionHandler extensionHandler;

    @Pointcut("@annotation(org.springframework.amqp.rabbit.annotation.RabbitHandler) " +
            "&& @annotation(org.springframework.amqp.rabbit.annotation.RabbitListener)")
    public void listenHandler() {}

    @Around("listenHandler()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        extensionHandler.preHandle();
        joinPoint.proceed();
        extensionHandler.postHandle();
    }
}
