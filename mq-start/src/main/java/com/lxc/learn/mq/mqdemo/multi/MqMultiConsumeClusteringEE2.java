package com.lxc.learn.mq.mqdemo.multi;

import com.vphonor.central.demo.facade.config.DemoConstant;
import com.vphonor.central.demo.facade.config.Hello;
import com.lxc.learn.mq.annotation.VPMqListener;
import com.lxc.learn.mq.core.MqMultiListener;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Component;

@Component
@VPMqListener(batchMaxSize = 3,consumerGroup = "EE", messageModel = MessageModel.CLUSTERING, topic = DemoConstant.User.SERVICE_SHORT_NAME, selectorExpress = "*", consumeThreadMax = 10)
public class MqMultiConsumeClusteringEE2 extends MqMultiConsume implements MqMultiListener<Hello> {
	public String getTag() {
		return "*";
	}

	@Override
	public String getGroup() {
		return "EE";
	}

	@Override
	public String getModel() {
		return MessageModel.CLUSTERING.getModeCN();
	}

}