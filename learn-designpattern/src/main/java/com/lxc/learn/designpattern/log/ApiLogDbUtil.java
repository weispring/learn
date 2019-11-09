package com.lxc.learn.designpattern.log;

import com.lxc.learn.common.util.JsonUtil;
import com.lxc.learn.common.util.collector.CollectioUtil;
import com.lxc.learn.common.util.web.StringUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 接口日志写入数据库工具类
 */
@Slf4j
@UtilityClass
public final class ApiLogDbUtil {

	/** 批量插入数据量 */
	private static final int BATCH_SYNC_LOG_SIZE = 128;
	/** 日志存储buffer（数据条数）大小 */
	private static final int LOG_BUFFER_SIZE = 1024 * 16;
	/** 日志信息缓存区 */
	private static final BlockingQueue<LogEntity> LOG_BUFFER_QUEUE = new LinkedBlockingQueue<>(LOG_BUFFER_SIZE);
	/** uip响应报文保存最大长度（数据库类型为text，最大长度为65535，舍掉零头） */
	private static final int UIP_RESPXML_MAX_LENGTH = 60000;


	/**
	 * 接口日志
	 * @param apiCode
	 * @param req
	 * @param resp
	 * @param reqStartTime
	 * @param reqEndTime
	 * @param resultCode
	 * @return
	 */
	public static boolean insertApiLog(String apiCode, Object req , Object resp,
			Date reqStartTime, Date reqEndTime, String resultCode) {
		LogEntity entity = new LogEntity();
		entity.setReqStartTime(reqStartTime);
		entity.setReqEndTime(reqEndTime);
		entity.setReq(JsonUtil.objectToJson(req));

		String respStr = StringUtil.subExceed(JsonUtil.objectToJson(resp), UIP_RESPXML_MAX_LENGTH);
		entity.setResp(respStr);

		entity.setReturnCode(resultCode);
		Integer reqTotalTime = (int) (reqEndTime.getTime() - reqStartTime.getTime());
		entity.setReqTotalTime(reqTotalTime);
		entity.setCreateBy(1L);
		entity.setCreatedTime(new Date());
		return LOG_BUFFER_QUEUE.offer(entity);
	}

	/**
	 * 将buffer中的日志同步到数据库
	 * @param logService
	 */
	static void syncBuffer(LogService logService) {
		// 每次批量同步的日志
		List<LogEntity> batchSyncLogs = new ArrayList<>();
		while (true) {
			try {
				LOG_BUFFER_QUEUE.drainTo(batchSyncLogs, BATCH_SYNC_LOG_SIZE);
				if (!CollectioUtil.isEmpty(batchSyncLogs)) {
					logService.addBatch(batchSyncLogs);
					batchSyncLogs.clear();
				}
				while (LOG_BUFFER_QUEUE.isEmpty()) {
					TimeUnit.SECONDS.sleep(10);
				}
			} catch (Exception e) {
				log.error("add AdapterLogEntity failed!!!", e);
			}
		}
	}

	/**
	 * 将buffer中的日志立即同步到数据库（用于服务关闭时）
	 * 
	 * @param logService
	 */
	static void syncBufferNow(LogService logService) {
		// 每次批量同步的日志
		List<LogEntity> batchSyncLogs = new ArrayList<>();
		while (!LOG_BUFFER_QUEUE.isEmpty()) {
			try {
				LOG_BUFFER_QUEUE.drainTo(batchSyncLogs, BATCH_SYNC_LOG_SIZE);
				if (!CollectionUtils.isEmpty(batchSyncLogs)) {
					logService.addBatch(batchSyncLogs);
					batchSyncLogs.clear();
				}
			} catch (Exception e) {
				log.error("add AdapterLogEntity failed!!!", e);
			}
		}

		log.info("log buffer sync is done!");
	}

}