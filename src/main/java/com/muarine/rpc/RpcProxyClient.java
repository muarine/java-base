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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * com.muarine.rpc.RpcProxyClient
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 16/8/10
 * @since 1.0
 */
public class RpcProxyClient implements InvocationHandler {

    private Object obj;

    public RpcProxyClient(Object obj) {
        this.obj = obj;
    }

    /**
     * 得到被代理对象;
     */
    public static Object getProxy(Object obj){
        return java.lang.reflect.Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(), new RpcProxyClient(obj));
    }

    /**
     * Processes a method invocation on a proxy instance and returns
     * the result.  This method will be invoked on an invocation handler
     * when a method is invoked on a proxy instance that it is
     * associated with.
     *
     * @param proxy  the proxy instance that the method was invoked on
     * @param method the {@code Method} instance corresponding to
     *               the interface method invoked on the proxy instance.  The declaring
     *               class of the {@code Method} object will be the interface that
     *               the method was declared in, which may be a superinterface of the
     *               proxy interface that the proxy class inherits the method through.
     * @param args   an array of objects containing the values of the
     *               arguments passed in the method invocation on the proxy instance,
     *               or {@code null} if interface method takes no arguments.
     *               Arguments of primitive types are wrapped in instances of the
     *               appropriate primitive wrapper class, such as
     *               {@code java.lang.Integer} or {@code java.lang.Boolean}.
     * @return the value to return from the method invocation on the
     * proxy instance.  If the declared return type of the interface
     * method is a primitive type, then the value returned by
     * this method must be an instance of the corresponding primitive
     * wrapper class; otherwise, it must be a type assignable to the
     * declared return type.  If the value returned by this method is
     * {@code null} and the interface method's return type is
     * primitive, then a {@code NullPointerException} will be
     * thrown by the method invocation on the proxy instance.  If the
     * value returned by this method is otherwise not compatible with
     * the interface method's declared return type as described above,
     * a {@code ClassCastException} will be thrown by the method
     * invocation on the proxy instance.
     * @throws Throwable the exception to throw from the method
     *                   invocation on the proxy instance.  The exception's type must be
     *                   assignable either to any of the exception types declared in the
     *                   {@code throws} clause of the interface method or to the
     *                   unchecked exception types {@code java.lang.RuntimeException}
     *                   or {@code java.lang.Error}.  If a checked exception is
     *                   thrown by this method that is not assignable to any of the
     *                   exception types declared in the {@code throws} clause of
     *                   the interface method, then an
     *                   {@link UndeclaredThrowableException} containing the
     *                   exception that was thrown by this method will be thrown by the
     *                   method invocation on the proxy instance.
     * @see UndeclaredThrowableException
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = new Object();
        /**
         *
         1）服务消费方（client）调用以本地调用方式调用服务；
         2）client stub接收到调用后负责将方法、参数等组装成能够进行网络传输的消息体；
            封装消息体内容包括:
                            接口名、方法名、参数类型和参数值、请求超时时间、唯一标识requestID
         3）client stub找到服务地址，并将消息发送到服务端；
         4）server stub收到消息后进行解码；
         5）server stub根据解码结果调用本地的服务；
         6）本地服务执行并将结果返回给server stub；
         7）server stub将返回结果打包成消息并发送至消费方；
         8）client stub接收到消息，并进行解码；
         9）服务消费方得到最终结果。
         */
        // 序列化 将数据结构或者对象转化成二进制串 , 以便于后续的网络传输

        return result;
    }

    public ResponseMessage invoke(RequestMessage requestMessage) {

        System.out.println(requestMessage.toString());

        return null;
    }
}
