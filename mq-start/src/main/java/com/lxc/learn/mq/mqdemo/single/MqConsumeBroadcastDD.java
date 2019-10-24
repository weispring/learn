package com.lxc.learn.mq.mqdemo.single;

import com.lxc.learn.common.util.core.BaseDto;
import com.lxc.learn.mq.annotation.VPMqListener;
import com.lxc.learn.mq.core.MqSingleListener;
import com.lxc.learn.mq.mqdemo.DemoConstant;
import com.lxc.learn.mq.mqdemo.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@VPMqListener(consumerGroup = "DD", messageModel = MessageModel.BROADCASTING, topic = DemoConstant.User.SERVICE_SHORT_NAME, selectorExpress = "*", consumeThreadMax = 10)
public class MqConsumeBroadcastDD extends MqConsume implements MqSingleListener<MqMessage> {
	@Override
	public String getTag() {
		return "*";
	}

	@Override
	public String getGroup() {
		return "DD";
	}

	@Override
	public String getModel() {
		return MessageModel.BROADCASTING.getModeCN();
	}

}