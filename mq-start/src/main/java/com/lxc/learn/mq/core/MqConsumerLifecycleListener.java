package com.lxc.learn.mq.core;

public interface MqConsumerLifecycleListener<T>
{
  void prepareStart(T paramT);
}
