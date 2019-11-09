package com.lxc.learn.designpattern.generateno;

/**
 * 移动TransactionId生成接口
 *
 * @author liyulin
 * @date 2018年11月26日下午2:50:29
 */
public interface ICmccTransactionIdService {

	/**
	 * 移动TransactionId生成
	 * @param transactionId
	 * @param tranactionoType
	 * @param bussinessCode
	 * @return
	 * @throws InterruptedException
	 */
	String generateTransactionId(String transactionId, String tranactionoType, String bussinessCode);

}