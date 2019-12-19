package com.lxc.learn.designpattern.generateno.constant;

import com.lxc.learn.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/9 14:11
 */
@Slf4j
public class TransactionIdConstant {

    public static String TRANSACTION_ID_NING_XIA = "NINGXIA";

    public static String TRANSACTION_SHAAN_XI = "SHAAN_XI";


    public static String BUSINESS_ORDER = "BUSINESS_ORDER";


    public static String BUSINESS_PAY = "BUSINESS_PAY";

    public static Long CACHE_TIME_OUT_1H = 60 * 60L;

    public static Long CACHE_TIME_OUT_1D = 24 * CACHE_TIME_OUT_1H;


    public static String getRedisKey(String type,String transactionId){
        return type + "_" + transactionId;
    }

    public static String getLockRedisKey(String type){
        return type;
    }

    public static String getNgingXiaRedisKeyPrefix(String business){
        return TRANSACTION_ID_NING_XIA + business + DateUtil.format(DateUtil.FORMATDAY);
    }

    public static Long getNgingXiaTransactonMax(String business){
        return 99999L;
    }
}
