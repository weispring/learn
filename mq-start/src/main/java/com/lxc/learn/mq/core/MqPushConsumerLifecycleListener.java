package com.lxc.learn.mq.core;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

public interface MqPushConsumerLifecycleListener extends MqConsumerLifecycleListener<DefaultMQPushConsumer>
{
}

