package com.lxc.learn.designpattern.log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;

/**
 * 服务关闭时，确保buffer中的所有日志写到数据库
 *
 */
@Service
@Slf4j
public class ApplicationClosedEventListener implements ApplicationListener<ContextClosedEvent> {


	/**
	 * kill -2 pid
	 * 或者 ctrl + c 才行
	 * @param event
	 */

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		log.info("{}", "服务关闭 ContextClosedEvent");
	}

}