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

package com.muarine.flume;

/**
 * com.muarine.flume.Channel
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 16/8/2
 * @since 1.0
 */
public interface Channel {

    /**
     * 作为管道     对Process进行管制    读取配置    命名
     *
     * Configure
     * Channel  start stop  ChannelProcess
     *
     java org.apache.flume.ApplicationNode

     context = loadProperties(properties);

     SourceThread

            ChannelProcessor processEventBatch(evnets)



     ChannelThread

             Selector       selector.getRequiredChannels(event);

             Interceptor        interceptorChain.intercept(events);

     SinkThread



     *
     */




}
