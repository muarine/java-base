/*
 * RT MAP, Home of Professional MAP
 * Copyright 2016 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */

package com.muarine.rpc;

import com.muarine.rpc.message.RequestMessage;
import com.muarine.rpc.request.Callback;

/**
 * com.muarine.rpc.Main
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 16/8/10
 * @since 1.0
 */
public class Main {


    public static void main(String[] args) {
        // 构造message
        RequestMessage requestMessage = new RequestMessage();

        Callback callback = new Callback();

        NioChannel channel = new NioChannel(callback);

        channel.setMessage(requestMessage);

        channel.writeAndFlush(requestMessage);

    }

}
