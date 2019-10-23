package com.lxc.learn.mq.annotation;

import com.lxc.learn.mq.core.ConsumeMode;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VPMqListener
{
  String consumerGroup() default "";

  String topic();

  String selectorExpress() default "*";

  ConsumeMode consumeMode() default ConsumeMode.CONCURRENTLY;

  MessageModel messageModel() default MessageModel.CLUSTERING;

  int consumeThreadMax() default 64;

  int batchMaxSize() default 1;
}

