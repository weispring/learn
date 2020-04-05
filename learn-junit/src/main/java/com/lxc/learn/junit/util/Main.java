package com.lxc.learn.junit.util;


import com.alibaba.fastjson.JSON;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/4
 */
public class Main {

    public static void main(String[] args) {
        EncryptionUtil encryptionUtil = new EncryptionUtil();
        Storer storer = new Storer();
        storer.setUserId(1192715615883624448L);
        storer.setOrderId(1243086181403463681L);
        storer.setIccid("89852121212265193527");
        storer.setMethod(99);
        storer.setPOSCode("POS code");
        storer.setShopCode("shop code");
        storer.setAccountNumber("account number");
        storer.setAccountCode("account code");
        storer.setTimestamp(System.currentTimeMillis());
        storer.setVersions("1.0.0");

        storer.setVersions("11.0.0");





        String s = encryptionUtil.create(storer.getUserId(), storer.getOrderId(), storer.getIccid(), storer.getMethod(),
                storer.getPOSCode(), storer.getShopCode(), storer.getAccountNumber(), "account code",
                storer.getTimestamp(), storer.getVersions());
        System.out.println(s);

        System.out.println(JSON.toJSONString(storer));
    }

}
