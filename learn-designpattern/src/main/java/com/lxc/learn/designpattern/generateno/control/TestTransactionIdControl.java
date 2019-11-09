package com.lxc.learn.designpattern.generateno.control;

import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.designpattern.generateno.CmccTransactionIdFactory;
import com.lxc.learn.designpattern.generateno.constant.TransactionIdConstant;
import com.lxc.learn.designpattern.generateno.req.TransactionIdReqBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/9 17:46
 */
@Slf4j
@RequestMapping("/transactionid")
@RestController
public class TestTransactionIdControl {

    @Autowired
    private CmccTransactionIdFactory cmccTransactionIdFactory;
    @RequestMapping("/testGenerateTransactionId")
    public Resp testGenerateTransactionId(@RequestBody Req<TransactionIdReqBody> req){
        String transactionId = cmccTransactionIdFactory.generateTransactionId(String.valueOf(req.getBody().getId()), TransactionIdConstant.TRANSACTION_ID_NING_XIA, TransactionIdConstant.BUSINESS_ORDER);
        return RespUtil.success(transactionId);
    }


}


