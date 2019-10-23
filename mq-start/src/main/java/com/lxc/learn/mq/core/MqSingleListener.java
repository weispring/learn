package com.lxc.learn.mq.core;

public interface MqSingleListener<T>
{
  public void onMessage(T paramT);
}

