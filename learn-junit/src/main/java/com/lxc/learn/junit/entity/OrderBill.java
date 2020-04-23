package com.lxc.learn.junit.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 订单信息
 * </p>
 *
 * @author li xian chun
 * @since 2020-04-17
 */
@TableName("order_bill")
public class OrderBill implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 订单金额总金额
     */
    private Long amount;
    /**
     * 支付状态（1：待支付；2：支付成功；3：支付失败；4：待退款；5：退款成功；6：退款失败）
     */
    @TableField("pay_state")
    private Integer payState;
    /**
     * 10待确认20待支付30已支付40已发货50已完成60已取消70退款中80已退款90退款失败
     */
    @TableField("order_status")
    private Integer orderStatus;
    /**
     * 购买人id（demo_user库t_user_info表id）
     */
    private Long buyer;
    /**
     * 创建时间
     */
    @TableField("sys_add_time")
    private Long sysAddTime;
    /**
     * 更新时间
     */
    @TableField("sys_upd_time")
    private Long sysUpdTime;
    /**
     * 删除时间
     */
    @TableField("sys_del_time")
    private Long sysDelTime;
    /**
     * 新增者
     */
    @TableField("sys_add_user")
    private Long sysAddUser;
    /**
     * 更新者
     */
    @TableField("sys_upd_user")
    private Long sysUpdUser;
    /**
     * 删除者
     */
    @TableField("sys_del_user")
    private Long sysDelUser;
    /**
     * 删除状态=={1:正常, 2:已删除}
     */
    @TableField("sys_del_state")
    private Integer sysDelState;

    @TableField("buyer_phone")
    private String buyerPhone;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getBuyer() {
        return buyer;
    }

    public void setBuyer(Long buyer) {
        this.buyer = buyer;
    }

    public Long getSysAddTime() {
        return sysAddTime;
    }

    public void setSysAddTime(Long sysAddTime) {
        this.sysAddTime = sysAddTime;
    }

    public Long getSysUpdTime() {
        return sysUpdTime;
    }

    public void setSysUpdTime(Long sysUpdTime) {
        this.sysUpdTime = sysUpdTime;
    }

    public Long getSysDelTime() {
        return sysDelTime;
    }

    public void setSysDelTime(Long sysDelTime) {
        this.sysDelTime = sysDelTime;
    }

    public Long getSysAddUser() {
        return sysAddUser;
    }

    public void setSysAddUser(Long sysAddUser) {
        this.sysAddUser = sysAddUser;
    }

    public Long getSysUpdUser() {
        return sysUpdUser;
    }

    public void setSysUpdUser(Long sysUpdUser) {
        this.sysUpdUser = sysUpdUser;
    }

    public Long getSysDelUser() {
        return sysDelUser;
    }

    public void setSysDelUser(Long sysDelUser) {
        this.sysDelUser = sysDelUser;
    }

    public Integer getSysDelState() {
        return sysDelState;
    }

    public void setSysDelState(Integer sysDelState) {
        this.sysDelState = sysDelState;
    }


    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    @Override
    public String toString() {
        return "OrderBill{" +
        "id=" + id +
        ", amount=" + amount +
        ", payState=" + payState +
        ", orderStatus=" + orderStatus +
        ", buyer=" + buyer +
        ", sysAddTime=" + sysAddTime +
        ", sysUpdTime=" + sysUpdTime +
        ", sysDelTime=" + sysDelTime +
        ", sysAddUser=" + sysAddUser +
        ", sysUpdUser=" + sysUpdUser +
        ", sysDelUser=" + sysDelUser +
        ", sysDelState=" + sysDelState +
        "}";
    }
}
