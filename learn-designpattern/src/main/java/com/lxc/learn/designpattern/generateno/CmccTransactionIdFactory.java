package com.lxc.learn.designpattern.generateno;

import com.lxc.learn.common.util.RuntimeBusinessException;
import com.lxc.learn.designpattern.generateno.constant.ReturnCode;
import com.lxc.learn.designpattern.generateno.constant.TransactionIdConstant;
import com.lxc.learn.designpattern.generateno.service.ningxia.NingXiaTransactionIdServiceImpl;
import com.lxc.learn.designpattern.generateno.service.shanxi.ShanxiTransactionIdServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 移动TransactionId生成工厂
 */
@Service
public class CmccTransactionIdFactory {

	/**
	 * 所有的TransactionIdService<br>
	 * Map(key, value)==>(appKeyPre, ICmccTransactionIdService)
	 */
	private static final Map<String, ICmccTransactionIdService> CMCC_TRANSACTIONID_SERVICES = new HashMap<>();

	@Autowired
	private NingXiaTransactionIdServiceImpl ningXiaTransactionIdServiceImpl;
	@Autowired
	private ShanxiTransactionIdServiceImpl shanxiTransactionIdServiceImpl;

	@PostConstruct
	private void initCmccTransactionIdService() {
		CMCC_TRANSACTIONID_SERVICES.put(TransactionIdConstant.TRANSACTION_ID_NING_XIA, ningXiaTransactionIdServiceImpl);
		CMCC_TRANSACTIONID_SERVICES.put(TransactionIdConstant.TRANSACTION_SHAAN_XI, shanxiTransactionIdServiceImpl);
	}

	public String generateTransactionId(String transactionId, String tranactionoType, String bussinessCode){
		ICmccTransactionIdService cmccTransactionIdService = CMCC_TRANSACTIONID_SERVICES.get(tranactionoType);
		if (null == cmccTransactionIdService) {
			throw new RuntimeBusinessException(
					ReturnCode.NOT_SUPPORTED_TRANSACTION_TYPE);
		}

		return cmccTransactionIdService.generateTransactionId(transactionId, tranactionoType, bussinessCode);
	}
}