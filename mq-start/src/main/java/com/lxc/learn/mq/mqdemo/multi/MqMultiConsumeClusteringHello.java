package com.lxc.learn.mq.mqdemo.multi;

import com.lxc.learn.mq.annotation.VPMqListener;
import com.lxc.learn.mq.core.MqMultiListener;
import com.lxc.learn.mq.mqdemo.MqMessage;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Component;

@Component
@VPMqListener(batchMaxSize = 3, consumerGroup = "KK", messageModel = MessageModel.CLUSTERING, topic = "MULTI_HELLO", selectorExpress = "*", consumeThreadMax = 1)
public class MqMultiConsumeClusteringHello extends MqMultiConsume implements MqMultiListener<MqMessage> {
	public String getTag() {
		return "*";
	}

	@Override
	public String getGroup() {
		return "KK";
	}

	@Override
	public String getModel() {
		return MessageModel.BROADCASTING.getModeCN();
	}

}