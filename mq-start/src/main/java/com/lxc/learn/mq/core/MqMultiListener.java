package com.lxc.learn.mq.core;

import java.util.List;

public interface MqMultiListener<T>
{
  void onMessage(List<T> paramList);
}

