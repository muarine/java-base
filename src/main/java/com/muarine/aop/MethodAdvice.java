/*
 * RT MAP, Home of Professional MAP
 * Copyright 2016 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */

package com.muarine.aop;

/**
 * com.muarine.aop.MethodAdvice
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 16/8/16
 * @since 1.0
 */
public interface MethodAdvice {

    <T> T before();

    <T> T after();

}
