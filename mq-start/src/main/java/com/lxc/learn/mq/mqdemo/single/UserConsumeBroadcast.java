package com.lxc.learn.mq.mqdemo.single;

import com.lxc.learn.common.util.core.BaseDto;
import com.lxc.learn.mq.annotation.VPMqListener;
import com.lxc.learn.mq.core.MqSingleListener;
import com.lxc.learn.mq.mqdemo.DemoConstant;
import com.lxc.learn.mq.mqdemo.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Component;
import java.util.LinkedList;
import java.util.Queue;

@Slf4j
@Component
@VPMqListener(consumerGroup = DemoConstant.User.SERVICE_SHORT_NAME, messageModel = MessageModel.BROADCASTING, topic = DemoConstant.Order.SERVICE_SHORT_NAME, selectorExpress = "Tag_Order_OrderReqBody", consumeThreadMax = 10)
public class UserConsumeBroadcast implements MqSingleListener<MqMessage> {
	Queue<MqMessage> list = new LinkedList<MqMessage>();

	@Override
	public void onMessage(MqMessage message) {
		log.info("UserConsumeBroadcasting onMessage====" + message);
		list.add(message);
	}

	public BaseDto getMessage() {
		return list.poll();
	}
}