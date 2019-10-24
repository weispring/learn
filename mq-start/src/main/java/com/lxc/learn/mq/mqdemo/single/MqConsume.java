package com.lxc.learn.mq.mqdemo.single;

import com.lxc.learn.common.util.core.BaseDto;
import com.lxc.learn.mq.mqdemo.MqMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
public abstract class MqConsume {

	Queue<MqMessage> list = new LinkedList<MqMessage>();

	public void onMessage(MqMessage message) {
		log.error("group:" + getGroup() + " model:" + getModel() + " tag:" + getTag() + " "
				+ this.getClass().getSimpleName() + " single onMessage====" + message);
		list.add(message);
	}

	public abstract String getTag();

	public abstract String getGroup();

	public abstract String getModel();

	public void clearMessage() {
		list.clear();
	}

	public MqMessage getMessage() {
		return list.poll();
	}

}