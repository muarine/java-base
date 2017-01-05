package com.muarine.rocketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * Producer
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 2017 1/4/17 15:30
 * @since 2.0.0
 */
public class Producer {

    public static void main(String[] args) throws MQClientException {

        //language=JSON
        String json = "";
        String regex = "[0-9]";

        DefaultMQProducer producer = new DefaultMQProducer("Producer");
        producer.setNamesrvAddr("192.168.14.162:9876");
        producer.setInstanceName("Producer-instance");
        producer.setVipChannelEnabled(false);
        producer.setDefaultTopicQueueNums(4);
//        producer.setCompressMsgBodyOverHowmuch();
//        producer.setMaxMessageSize();
//        producer.setProducerGroup();
//        producer.setSendMessageWithVIPChannel();
        producer.start();

        try {
            for (int i = 0; i < 1000; i++) {
                Message msg = new Message("Topic1",
                                            "Tag1",
                                            "OrderId" + i,
                                            ("Body" + i).getBytes());
                SendResult sendResult = producer.send(msg);
                System.out.println(sendResult);
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }


    }

}
