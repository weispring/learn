package com.lxc.learn.designpattern.generateno;

import com.lxc.learn.common.util.RuntimeBusinessException;
import com.lxc.learn.common.util.core.ReturnCode;
import com.lxc.learn.common.util.web.StringUtil;
import com.lxc.learn.designpattern.generateno.constant.TransactionIdConstant;
import com.lxc.learn.designpattern.generateno.service.IGenerateCmccTransactionIdService;
import com.lxc.learn.redis.config.RedisAgent;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

import static com.lxc.learn.designpattern.generateno.constant.ReturnCode.LOCK_FAIL;
import static com.lxc.learn.designpattern.generateno.constant.ReturnCode.LOCK_INTERRUPTED;

/**
 * 移动TransactionId生成抽象类
 */
public abstract class AbstractCmccTransactionIdService implements ICmccTransactionIdService{

	@Autowired
	private RedisAgent redisAgent;
	@Autowired
	private RedissonClient redisson;

	public abstract IGenerateCmccTransactionIdService getGenerateCmccTransactionIdService(String bussinessCode);

	public String generateTransactionId(String transactionId, String tranactionoType, String bussinessCode) {
		Assert.notNull(transactionId, "vpTransactionId must be not null!");
		Assert.notNull(transactionId, "bussinessCode must be not null!");

		String redisKey = TransactionIdConstant.getRedisKey(tranactionoType,transactionId);
		String cmccTransactionId = redisAgent.getKey(redisKey);
		if (StringUtil.isNotEmpty(cmccTransactionId)) {
			return cmccTransactionId;
		}
		String redisKeyLock = TransactionIdConstant.getLockRedisKey(tranactionoType);
		RLock lock = redisson.getLock(redisKeyLock);

		//double check
		try {
			// 1、获取锁
			boolean lockBool = lock.tryLock(1000, 1000, TimeUnit.MILLISECONDS);
			if (!lockBool) {
				throw new RuntimeBusinessException(LOCK_FAIL);
			}

			// 2、获取成功
			// 2.1再次从redis取一次，有则返回
			cmccTransactionId = redisAgent.getKey(redisKey);
			if (StringUtil.isNotEmpty(cmccTransactionId)) {
				return cmccTransactionId;
			}

			// 2.2redis不存在
			// 2.2.1生成transactionId
			cmccTransactionId = generateTransactionId(bussinessCode);
			// 2.2.2放入redis
			boolean cacheBool = redisAgent.setKey(redisKey, cmccTransactionId, TransactionIdConstant.CACHE_TIME_OUT_1H);
			if (!cacheBool) {
				throw new RuntimeBusinessException(ReturnCode.FAIL);
			}
			return cmccTransactionId;
		} catch (InterruptedException e){
			throw new RuntimeBusinessException(LOCK_INTERRUPTED);
		}finally {
			// 3、释放锁
			lock.unlock();
		}
	}

	private String generateTransactionId(String bussinessCode) {
		IGenerateCmccTransactionIdService generateCmccTransactionIdService = getGenerateCmccTransactionIdService(
				bussinessCode);
		if (null == generateCmccTransactionIdService) {
			throw new RuntimeBusinessException(com.lxc.learn.designpattern.generateno.constant.ReturnCode.NOT_SUPPORTED_BUSINESS_TYPE);
		}
		return generateCmccTransactionIdService.generateTransactionId();
	}

}