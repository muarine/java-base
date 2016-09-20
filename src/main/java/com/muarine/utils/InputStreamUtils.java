/*
 * RT MAP, Home of Professional MAP
 * Copyright 2016 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */

package com.muarine.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * com.rtmap.wcp.utils.InputStreamUtils
 *
 * @author Muarine<maoyun@rtmap.com>
 * @date 16/9/19
 * @since 1.0
 */
public class InputStreamUtils {

    public static String getStringFromInputStream(InputStream inputStream){

        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        String line ;
        reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while((line = reader.readLine()) != null){
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{

            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }
}
