/*
 * RT MAP, Home of Professional MAP
 * Copyright 2016 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */

package com.muarine.rpc.message;

import java.io.Serializable;

/**
 * com.muarine.rpc.message.ResponseMessage
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 16/8/10
 * @since 1.0
 */
public class ResponseMessage implements Serializable{

    private Long requestID;
    private String code;
    private Object result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getRequestID() {
        return requestID;
    }

    public void setRequestID(Long requestID) {
        this.requestID = requestID;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
