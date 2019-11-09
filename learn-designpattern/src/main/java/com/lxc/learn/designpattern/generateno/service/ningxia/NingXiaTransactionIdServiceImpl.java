package com.lxc.learn.designpattern.generateno.service.ningxia;

import com.lxc.learn.designpattern.generateno.AbstractCmccTransactionIdService;
import com.lxc.learn.designpattern.generateno.constant.TransactionIdConstant;
import com.lxc.learn.designpattern.generateno.service.IGenerateCmccTransactionIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 宁夏移动TransactionId生成实现
 */
@Service
public class NingXiaTransactionIdServiceImpl extends AbstractCmccTransactionIdService {

	/**
	 * 所有的IGenerateCmccTransactionIdService<br>
	 * Map(key, value)==>(bussinessCode, IGenerateCmccTransactionIdService)
	 */
	private static final Map<String, IGenerateCmccTransactionIdService> GENERATE_CMCC_TRANSACTIONID_SERVICES = new HashMap<>();

	@Autowired
	private NingxiaPayTransactionIdServiceImpl ningxiaCmccOpenAccountTransactionIdServiceImpl;
	@Autowired
	private NingxiaOrderTransactionIdServiceImpl ningxiaCmccDefaultTransactionIdServiceImpl;

	@PostConstruct
	private void initCmccTransactionIdService() {
		// 开户
		GENERATE_CMCC_TRANSACTIONID_SERVICES.put(TransactionIdConstant.BUSINESS_ORDER,
				ningxiaCmccOpenAccountTransactionIdServiceImpl);
	}

	@Override
	public IGenerateCmccTransactionIdService getGenerateCmccTransactionIdService(String bussinessCode) {
		IGenerateCmccTransactionIdService generateCmccTransactionIdService = GENERATE_CMCC_TRANSACTIONID_SERVICES
				.get(bussinessCode);
		// 找不到businessCode对应的service，就用默认的
		if (null == generateCmccTransactionIdService) {
			return ningxiaCmccDefaultTransactionIdServiceImpl;
		}

		return generateCmccTransactionIdService;
	}

}