package com.lxc.learn.mq.mqdemo.multi;

import com.lxc.learn.common.util.core.BaseDto;
import com.lxc.learn.mq.mqdemo.MqMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
public abstract class MqMultiConsume {

	Queue<List<MqMessage>> list = new LinkedList<List<MqMessage>>();

	public void onMessage(List<MqMessage> messages) {
        log.error("group:" + getGroup() + " model:" + getModel() + " tag:" + getTag() + " "
                + this.getClass().getSimpleName() + " multi onMessage====" + messages);

		list.add(messages);

	}

	public abstract String getTag();

	public abstract String getGroup();

	public abstract String getModel();

	public void clearMessage() {
		log.info("clearMessage multi model:" + getModel() + " group:" + getGroup() + " tag:" + getTag() + " "
				+ this.getClass().getSimpleName() + "clear ");
		list.clear();
	}

	public List<MqMessage> getMessage() {
		return list.poll();
	}

}