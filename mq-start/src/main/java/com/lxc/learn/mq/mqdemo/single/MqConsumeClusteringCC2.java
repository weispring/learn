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
@VPMqListener(consumerGroup = "CC", messageModel=MessageModel.CLUSTERING,topic = DemoConstant.User.SERVICE_SHORT_NAME, selectorExpress = "*", consumeThreadMax = 10)
public class MqConsumeClusteringCC2 extends MqConsume implements MqSingleListener<BaseDto> {
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