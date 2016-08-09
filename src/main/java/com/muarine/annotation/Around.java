/*
 *
 *  *
 *  *  * RT MAP, Home of Professional MAP
 *  *  * Copyright 2016 Bit Main Inc. and/or its affiliates and other contributors
 *  *  * as indicated by the @author tags. All rights reserved.
 *  *  * See the copyright.txt in the distribution for a
 *  *  * full listing of individual contributors.
 *  *
 *
 */

package com.muarine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * com.muarine.annotation.Around
 *
 * @author Muarine<maoyun@rtmap.com>
 * @date 16/7/15
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Around {

    String value() default "";

    String argNames() default "";
}
