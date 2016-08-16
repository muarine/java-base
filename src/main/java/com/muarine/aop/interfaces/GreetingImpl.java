/*
 * RT MAP, Home of Professional MAP
 * Copyright 2016 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */

package com.muarine.aop.interfaces;

/**
 * com.muarine.aop.interfaces.GreetingImpl
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 16/8/16
 * @since 1.0
 */
public class GreetingImpl implements Greeting{


    @Override
    public void doSomething() {
        System.out.println("doSomething...");
    }

    public void before(){
        System.out.println("before");
    }

    public void after(){
        System.out.println("after");
    }


    public static void main(String[] args) {

        GreetingImpl greeting = new GreetingImpl();
        greeting.before();
        greeting.doSomething();
        greeting.after();

    }
}
