package com.lxc.learn.mq.mqdemo.multi;

import com.lxc.learn.common.util.core.BaseDto;
import com.lxc.learn.mq.annotation.VPMqListener;
import com.lxc.learn.mq.core.MqMultiListener;
import com.lxc.learn.mq.mqdemo.DemoConstant;
import com.lxc.learn.mq.mqdemo.MqMessage;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Component;

@Component
@VPMqListener(batchMaxSize = 3, consumerGroup = "AA", messageModel = MessageModel.BROADCASTING, topic = DemoConstant.User.SERVICE_SHORT_NAME, selectorExpress = "tagA", consumeThreadMax = 10)
public class MqMultiConsumeBroadcastAATagA extends MqMultiConsume implements MqMultiListener<MqMessage> {
	public String getTag() {
		return "tagA";
	}

	@Override
	public String getGroup() {
		return "AA";
	}

	@Override
	public String getModel() {
		return MessageModel.BROADCASTING.getModeCN();
	}

}