package com.lxc.learn.designpattern.generateno.service.ningxia;

import com.lxc.learn.common.util.DateUtil;
import com.lxc.learn.common.util.RuntimeBusinessException;
import com.lxc.learn.designpattern.generateno.constant.TransactionIdConstant;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.lxc.learn.designpattern.generateno.constant.ReturnCode.INCR_FAIL;

/**
 * 开户transactionId生成
 *
 * @author liyulin
 * @date 2018年11月26日下午6:15:28
 */
@Service
public class NingxiaPayTransactionIdServiceImpl extends AbstractNingXiaGenerateCmccTransactionIdService {

	/**
	 * 【开户】交易流水号编码规则
	 * 
	 * <p>
	 * 格式如下：接入源编码（接入方是6位，实名认证平台是4位）+
	 * 14位组包时间YYYYMMDDHH24MMSS+6位流水号（定长），序号从000001开始，增量步长为1
	 */
	@Override
	public String generateTransactionId() {
		Long incr = this.redisAgent.incrementReturnLong(getCmNoIncrRedisKeyPrefix(),1L);
		if (incr == null || incr == 0){
			throw new RuntimeBusinessException(INCR_FAIL);
		}
		Long remainder = incr %  getTransactionidIncrMax();
		String nowTime = DateUtil.format(DateUtil.FORMATDAY_SS);
		Random ran = new Random();
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