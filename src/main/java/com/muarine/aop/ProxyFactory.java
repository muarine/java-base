/*
 * RT MAP, Home of Professional MAP
 * Copyright 2016 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */

package com.muarine.aop;

import com.muarine.aop.interfaces.Greeting;
import com.muarine.aop.interfaces.GreetingImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * com.muarine.aop.ProxyFactory
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 16/8/16
 * @since 1.0
 */
public class ProxyFactory implements MethodInterceptor {

    private Class<?> clazz;
    private MethodAdvice methodAdvice;

    public ProxyFactory() {
    }

    public void setTarget(Class<?> clazz){
        this.clazz = clazz;
    }

    public void setAdvice(MethodAdvice methodAdvice){
        this.methodAdvice = methodAdvice;
    }

    private <T> T getProxy() {
        return (T)Enhancer.create(clazz, this);
    }

    /**
     *
     * @param o
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        methodAdvice.before();
        Object result = methodProxy.invokeSuper(o , objects);
        methodAdvice.after();
        return result;
    }

    /**
     * main
     * @param args
     */
    public static void main(String[] args) {

        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(GreetingImpl.class);
        factory.setAdvice(new GreetingMethodAdvice());

        Greeting greeting = factory.getProxy();
        greeting.doSomething();
    }
}
