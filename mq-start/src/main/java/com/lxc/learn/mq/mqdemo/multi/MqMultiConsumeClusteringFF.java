package com.lxc.learn.mq.mqdemo.multi;

import com.lxc.learn.mq.annotation.VPMqListener;
import com.lxc.learn.mq.core.MqMultiListener;
import com.lxc.learn.mq.mqdemo.DemoConstant;
import com.lxc.learn.mq.mqdemo.MqMessage;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Component;

@Component
@VPMqListener(batchMaxSize = 3,consumerGroup = "FF", messageModel = MessageModel.CLUSTERING, topic = DemoConstant.User.SERVICE_SHORT_NAME, selectorExpress = "*", consumeThreadMax = 10)
public class MqMultiConsumeClusteringFF extends MqMultiConsume implements MqMultiListener<MqMessage> {
	public String getTag() {
		return "*";
	}

	@Override
	public String getGroup() {
		return "FF";
	}

	@Override
	public String getModel() {
		return MessageModel.CLUSTERING.getModeCN();
	}

}