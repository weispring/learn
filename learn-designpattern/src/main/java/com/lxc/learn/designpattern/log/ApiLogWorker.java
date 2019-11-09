package com.lxc.learn.designpattern.log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 接口日志工作服务
 */
@Service
class ApiLogWorker {

	@Autowired
	private LogService logService;

	@PostConstruct
	public void init() {
		// 接口日志线程数量大小
		int threadCount = Runtime.getRuntime().availableProcessors();
		for (int i = 0; i < threadCount; i++) {
			String threadName = "api_log_thread" + i;
			// 接口日志线程
			new Thread(() -> ApiLogDbUtil.syncBuffer(logService), threadName).start();
		}
	}

}