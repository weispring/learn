package com.lxc.learn.mq.mqdemo.multi;

import com.lxc.learn.common.util.core.BaseDto;
import com.lxc.learn.mq.annotation.VPMqListener;
import com.lxc.learn.mq.core.MqMultiListener;
import com.lxc.learn.mq.mqdemo.DemoConstant;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Component;

@Component
@VPMqListener(batchMaxSize = 3, consumerGroup = "CC", messageModel = MessageModel.CLUSTERING, topic = DemoConstant.User.SERVICE_SHORT_NAME, selectorExpress = "*", consumeThreadMax = 10)
public class MqMultiConsumeClusteringCC extends MqMultiConsume implements MqMultiListener<BaseDto> {
	public String getTag() {
		return "*";
	}

	@Override
	public String getGroup() {
		return "CC";
	}

	@Override
	public String getModel() {
		return MessageModel.CLUSTERING.getModeCN();
	}

}