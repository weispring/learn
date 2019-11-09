package com.lxc.learn.designpattern.generateno.service.ningxia;

import com.lxc.learn.common.util.DateUtil;
import com.lxc.learn.common.util.RuntimeBusinessException;
import com.lxc.learn.designpattern.generateno.constant.TransactionIdConstant;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.lxc.learn.designpattern.generateno.constant.ReturnCode.INCR_FAIL;
import static com.lxc.learn.designpattern.generateno.constant.ReturnCode.INCR_LIMIT;

/**
 * 默认的transactionId生成
 * 
 * <p>
 * 找不到businessCode对应的service，就用默认的
 *
 * @author liyulin
 * @date 2018年11月28日上午11:07:09
 */
@Service
public class NingxiaOrderTransactionIdServiceImpl extends AbstractNingXiaGenerateCmccTransactionIdService {


	@Override
	public String generateTransactionId() {
		Long incr = this.redisAgent.incrementReturnLong(getCmNoIncrRedisKeyPrefix(),1L);
		if (incr == null || incr == 0){
			throw new RuntimeBusinessException(INCR_FAIL);
		}
		Long remainder = incr /  getTransactionidIncrMax();
		if (remainder > 1){
			throw new RuntimeBusinessException(INCR_LIMIT);
		}
		String nowTime = DateUtil.format(DateUtil.FORMATDAY_SS);
		StringBuffer no = new StringBuffer(6);
		int length = incr.toString().length();
		if (length < 6){
			int count = 6 - length;
			while (count > 0){
				no.append("0");
				--count;
			}
		}
		no.append(incr.toString());
		return "1085" + nowTime + no.toString();
	}

	@Override
	protected String getCmNoIncrRedisKeyPrefix() {
		return TransactionIdConstant.getNgingXiaRedisKeyPrefix(TransactionIdConstant.BUSINESS_ORDER);
	}

	@Override
	protected Long getTransactionidIncrMax() {
		return TransactionIdConstant.getNgingXiaTransactonMax(TransactionIdConstant.BUSINESS_ORDER);
	}
}