/*
 * RT MAP, Home of Professional MAP
 * Copyright 2016 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */

package com.muarine.rpc;

import com.muarine.rpc.message.RequestMessage;
import com.muarine.rpc.message.ResponseMessage;
import com.muarine.rpc.request.Callback;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * com.muarine.rpc.NioChannel
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 16/8/10
 * @since 1.0
 */
public class NioChannel {

    private static final Map<Long , Object> responseMap = new ConcurrentHashMap<Long , Object>() ;
    private RequestMessage message;
    private AtomicLong number = new AtomicLong(1);
    private Callback callback;

    public NioChannel(Callback callback) {
        this.callback = callback;
    }


    public void setMessage(RequestMessage message) {
        this.message = message;
    }

    public void writeAndFlush(RequestMessage requestMessage) {
        requestMessage.setRequestID(number.incrementAndGet());
        // RPC远程调用
        RpcProxyClient client = new RpcProxyClient(new Object());
        ResponseMessage res = client.invoke(message);
        responseMap.put(res.getRequestID() , res);
        this.call(res);
    }

    public void call(ResponseMessage res) {

        callback.setDone(res);

    }
}
