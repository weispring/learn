package com.lxc.learn.mq.mqdemo.single;

import com.vphonor.central.demo.facade.config.DemoConstant;
import com.vphonor.central.demo.facade.config.Hello;
import com.lxc.learn.mq.annotation.VPMqListener;
import com.lxc.learn.mq.core.MqSingleListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@VPMqListener(consumerGroup = "DD", messageModel = MessageModel.BROADCASTING, topic = DemoConstant.User.SERVICE_SHORT_NAME, selectorExpress = "*", consumeThreadMax = 10)
public class MqConsumeBroadcastDD extends MqConsume implements MqSingleListener<Hello> {
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