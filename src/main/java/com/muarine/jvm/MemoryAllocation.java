/* 
 * RT MAP, Home of Professional MAP 
 * Copyright 2017 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.muarine.jvm;

/**
 * @author maoyun@rtmap.com
 * @project hello
 * @package com.muarine.jvm
 * @date 4/6/17
 */
public class MemoryAllocation {

    /**
     * VM 参数：-verbose: gc- Xms20M- Xmx20M- Xmn10M- XX:+ PrintGCDetails -XX: SurvivorRatio= 8
     * @param args
     */
    public static void main(String[] args) {

        byte[] allocation1,allocation2,allocation3,allocation4; 
        allocation1= new byte[ 2*1024*1024];
        allocation2= new byte[ 2*1024*1024];
        allocation3= new byte[ 2*1024*1024];
        allocation4= new byte[ 4*1024*1024];// 出现 一次 Minor GC

    }
}
