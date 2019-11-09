package com.lxc.learn.designpattern.generateno.service.shanxi;

import com.lxc.learn.designpattern.generateno.AbstractCmccTransactionIdService;
import com.lxc.learn.designpattern.generateno.constant.TransactionIdConstant;
import com.lxc.learn.designpattern.generateno.service.IGenerateCmccTransactionIdService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShanxiTransactionIdServiceImpl extends AbstractCmccTransactionIdService {

	/**
	 * 所有的IGenerateCmccTransactionIdService<br>
	 * Map(key, value)==>(bussinessCode, IGenerateCmccTransactionIdService)
	 */
	private static final Map<String, IGenerateCmccTransactionIdService> GENERATE_CMCC_TRANSACTIONID_SERVICES = new HashMap<>();

	@PostConstruct
	private void initCmccTransactionIdService() {
		// 下单transactionId生成
		GENERATE_CMCC_TRANSACTIONID_SERVICES.put(TransactionIdConstant.BUSINESS_ORDER,
				null);
		// 支付transactionId生成
		GENERATE_CMCC_TRANSACTIONID_SERVICES.put(TransactionIdConstant.BUSINESS_PAY,
				null);
	}

	@Override
	public IGenerateCmccTransactionIdService getGenerateCmccTransactionIdService(String bussinessCode) {
		return GENERATE_CMCC_TRANSACTIONID_SERVICES.get(bussinessCode);
	}
	
}