package com.lxc.learn.junit.util;

import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/3/27.
 */
@Accessors(chain = true)
public class Storer implements Serializable {
    private Long userId;
    private Long orderId;
    private String appKey;
    private String iccid;
    private Integer method;
    /**
     *
     */
    private String POSCode;
    /**
     * 便利店code
     */
    private String shopCode;
    /**
     * 操作人账号
     */
    private String accountNumber;
    /**
     * 操作人编号
     */
    private String accountCode;

    private Long timestamp;
    private String versions;
    private String digest;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public String getPOSCode() {
        return POSCode;
    }

    public void setPOSCode(String POSCode) {
        this.POSCode = POSCode;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
}
