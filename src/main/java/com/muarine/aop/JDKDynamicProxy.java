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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * com.muarine.aop.JDKDynamicProxy
 * JDK动态代理
 *
 * JDK 给我们提供的动态代理只能代理接口，而不能代理没有接口的类
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 16/8/16
 * @since 1.0
 */
public class JDKDynamicProxy implements InvocationHandler{

    private Object target;

    public JDKDynamicProxy(Object target){
        this.target = target;
    }

    public <T> T getProxy(){
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader() ,
                target.getClass().getInterfaces() ,
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        this.before();
        Object result = method.invoke(target, args);
        this.after();
        return result;
    }

    private void before(){
        System.out.println("before");
    }

    private void after(){
        System.out.println("after");
    }


    public static void main(String[] args) throws Exception {

        Greeting greeting = new JDKDynamicProxy(new GreetingImpl()).getProxy();
        greeting.doSomething();

    }

}
