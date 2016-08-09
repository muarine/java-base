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

package com.muarine.algorithms;

/**
 * com.muarine.algorithms.TestAlgorithms
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 16/8/2
 * @since 1.0
 */
public class TestAlgorithms {


    public static void main(String[] args) {

        String url = "http://www.baidu.com?key=asdasdadsdas&method=post&operator=add";

        int r1 = HashAlgorithms.additiveHash(url , 13);
        System.out.println(r1);

        int r2 = HashAlgorithms.java(url);
        System.out.println();

    }



}
