import com.lxc.learn.common.util.core.BaseDto;
import com.lxc.learn.mq.core.MqAgent;
import com.lxc.learn.mq.mqdemo.DemoConstant;
import com.lxc.learn.mq.mqdemo.multi.MqMultiConsumeClusteringHello;
import com.lxc.learn.mq.mqdemo.single.MqConsumeBroadcastBBPerformance;
import com.lxc.learn.mq.mqdemo.single.MqConsumeBroadcastDD;
import com.lxc.learn.mq.mqdemo.single.UserConsumeBroadcast;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class MqProducterTest {
	@Autowired
	private MqConsumeBroadcastDD normal;
	@Autowired
	private MqMultiConsumeClusteringHello multi;

	@Autowired
	private MqConsumeBroadcastBBPerformance performance;
	@Autowired
	private UserConsumeBroadcast userConsumeBroadcasting;

	@Autowired
	private MqAgent mqAgent;

	@Before
	public void init() {

	}

	@Test(timeout = 60000)
	public void testMqSend() throws InterruptedException {
		String uuid = UUID.randomUUID().toString();
		Hello hello = new Hello();
		hello.setId(uuid);
		System.out.println("send normal message=" + hello);
		normal.clearMessage();
		mqAgent.syncSend(DemoConstant.User.SERVICE_SHORT_NAME, "tagA", hello);
		BaseDto message = null;
		while (true) {
			message = normal.getMessage();
			if (message != null && ((Hello)message).id.equals(uuid))
				break;
			else if (message != null) {
				System.out.println("receive for old message=" + message);
			}
			Thread.sleep(1000);
		}
		assertNotNull(message);
		// log.info("receive for current message=" + message);
	}

	@Test(timeout = 60000)
	public void testMqSendVpReq() throws InterruptedException {

		BaseDto baseDto = new Hello();
		String uuid = UUID.randomUUID().toString();
		System.out.println("send vp message=" + baseDto);
		mqAgent.syncSend(DemoConstant.Order.SERVICE_SHORT_NAME, "Tag_Order_OrderReqBody", baseDto);
		BaseDto message = null;
		while (true) {
			message = userConsumeBroadcasting.getMessage();
			if (message != null)
				break;
			else if (message != null) {
				// log.info("receive for old message=" + message);
			}
			Thread.sleep(1000);
		}
		assertNotNull(message);
		// log.info("receive for current message=" + message);
	}

	@Test(timeout = 60000)
	public void testMqSendPerformance() throws InterruptedException {
		long begin = System.currentTimeMillis();
		int sendCount = 1000;
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < sendCount; i++) {
			String uuid = UUID.randomUUID().toString();
			BaseDto hello = new BaseDto();
			((Hello)hello).setId(uuid);
			map.put(uuid, "1");
			mqAgent.syncSend(DemoConstant.User.SERVICE_SHORT_NAME, "tagA", hello);
		}

		int receCount = 0;
		while (true) {
			Hello hello = (Hello) performance.getMessage();
			if (hello != null && map.containsKey(hello.getId()))
				receCount++;
			else if (hello != null) {
				System.out.println("old message:" + hello.getId());
			} else {
				Thread.sleep(50);
			}
			if (sendCount == receCount) {
				break;
			}
		}
		long end = System.currentTimeMillis();
		log.info("发送{}条数据,接收总共耗时：{}秒", sendCount, (end - begin) / 1000);
		assertEquals(sendCount, receCount);
		// log.info("receive for current message=" + message);
	}

	@Test(timeout = 60000)
	public void testMqSendMulti() throws InterruptedException {
		multi.clearMessage();
		int sendCount = 10;
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < sendCount; i++) {
			String uuid = UUID.randomUUID().toString();
			BaseDto hello = new Hello();
			((Hello)hello).setId(uuid);
			map.put(uuid, "1");
			mqAgent.syncSend("MULTI_HELLO", "*", hello);
		}
		int receCount = 0;
		while (true) {
			List<BaseDto> lists = multi.getMessage();
			if (lists == null) {
				Thread.sleep(500);
				continue;
			}
			for (BaseDto h : lists) {
				if (map.containsKey(((Hello)h).id)) {
					receCount++;
				}
			}
			if (sendCount == receCount)
				break;
		}

		assertEquals(sendCount, receCount);
	}

}
