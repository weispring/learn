package com.lxc.learn.mq.mqdemo.single;

import com.lxc.learn.common.util.core.BaseDto;
import com.lxc.learn.mq.annotation.VPMqListener;
import com.lxc.learn.mq.core.MqSingleListener;
import com.lxc.learn.mq.mqdemo.DemoConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@VPMqListener(consumerGroup = "AA", messageModel = MessageModel.BROADCASTING, topic = DemoConstant.User.SERVICE_SHORT_NAME, selectorExpress = "tagA", consumeThreadMax = 10)
public class MqConsumeBroadcastAATagA extends MqConsume implements MqSingleListener<BaseDto> {
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