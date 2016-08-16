/*
 * RT MAP, Home of Professional MAP
 * Copyright 2016 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */

package com.muarine.aop;

/**
 * com.muarine.aop.GreetingMethodAdvice
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 16/8/16
 * @since 1.0
 */
public class GreetingMethodAdvice implements MethodAdvice{

    @Override
    public void before() {
        System.out.println("factory before");
    }

    @Override
    public void after() {
        System.out.println("factory after");
    }
}
