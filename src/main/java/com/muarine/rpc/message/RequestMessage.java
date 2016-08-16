/*
 * RT MAP, Home of Professional MAP
 * Copyright 2016 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */

package com.muarine.rpc.message;

import java.util.Map;

/**
 * com.muarine.rpc.message.RequestMessage
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 16/8/10
 * @since 1.0
 */
public class RequestMessage {

    //接口名、方法名、参数类型和参数值、请求超时时间、唯一标识requestID
    private Long requestID;
    private String interfaceName;
    private String methodName;
    private Map<Class , Object> params;
    private Integer timeout;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Map<Class, Object> getParams() {
        return params;
    }

    public void setParams(Map<Class, Object> params) {
        this.params = params;
    }

    public Long getRequestID() {
        return requestID;
    }

    public void setRequestID(Long requestID) {
        this.requestID = requestID;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RequestMessage{");
        sb.append("interfaceName='").append(interfaceName).append('\'');
        sb.append(", requestID=").append(requestID);
        sb.append(", methodName='").append(methodName).append('\'');
        sb.append(", params=").append(params);
        sb.append(", timeout=").append(timeout);
        sb.append('}');
        return sb.toString();
    }
}
