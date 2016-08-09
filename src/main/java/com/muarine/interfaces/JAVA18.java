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

package com.muarine.interfaces;

/**
 * com.muarine.interfaces.JAVA18
 *
 * @author Muarine<maoyun@rtmap.com>
 * @date 16/7/15
 * @since 1.0
 */
public interface JAVA18 {

    double calculates(int a);

    default double calucate(int a){
        return Math.sqrt(a);
    }
}
