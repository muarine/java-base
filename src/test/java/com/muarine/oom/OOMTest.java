/*
 * RT MAP, Home of Professional MAP
 * Copyright 2017 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.muarine.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maoyun@rtmap.com
 * @project hello
 * @package com.muarine.oom
 * @date 5/4/18
 */
public class OOMTest {

    private String name;
    private String text;

    public OOMTest(String name, String text) {
        this.name = name;
        this.text = text;
    }

    private static List<OOMTest> oomTests = new ArrayList<>();

    public static void main(String[] args) {

        // 测试OutOfMemory
        while (true){
            oomTests.add(new OOMTest("abcdefgabcdefgabcdefgabcdefgabcdefg", "abcdefgabcdefgabcdefgabcdefgabcdefgabcdefg"));
        }

    }
}
