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

/**
 * com.muarine.aop.GreetingStaticProxy
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 16/8/16
 * @since 1.0
 */
public class GreetingStaticProxy implements Greeting {

    private GreetingImpl greetingImpl;

    public GreetingStaticProxy(GreetingImpl greetingImpl){
        this.greetingImpl = greetingImpl;
    }

    @Override
    public void doSomething() throws Exception {
        greetingImpl.before();
        greetingImpl.doSomething();
        greetingImpl.after();
    }

    public static void main(String[] args) throws Exception {

        GreetingStaticProxy proxy = new GreetingStaticProxy(new GreetingImpl());
        proxy.doSomething();

    }
}
