package com.lxc.learn.junit.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 订单商品信息
 * </p>
 *
 * @author li xian chun
 * @since 2020-04-17
 */
@TableName("order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 订单id
     */
    @TableField("order_id")
    private Long orderId;
    /**
     * 商品名稱
     */
    @TableField("product_name")
    private String productName;
    /**
     * 商品id
     */
    @TableField("product_id")
    private Long productId;
    /**
     * skuid
     */
    @TableField("sku_id")
    private Long skuId;
    /**
     * 价格
     */
    private Long price;
    /**
     * 数量
     */
    private Long count;
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getsysAddTime() {
        return sysAddTime;
    }

    public void setsysAddTime(Long sysAddTime) {
        this.sysAddTime = sysAddTime;
    }

    public Long getsysUpdTime() {
        return sysUpdTime;
    }

    public void setsysUpdTime(Long sysUpdTime) {
        this.sysUpdTime = sysUpdTime;
    }

    public Long getsysDelTime() {
        return sysDelTime;
    }

    public void setsysDelTime(Long sysDelTime) {
        this.sysDelTime = sysDelTime;
    }

    public Long getsysAddUser() {
        return sysAddUser;
    }

    public void setsysAddUser(Long sysAddUser) {
        this.sysAddUser = sysAddUser;
    }

    public Long getsysUpdUser() {
        return sysUpdUser;
    }

    public void setsysUpdUser(Long sysUpdUser) {
        this.sysUpdUser = sysUpdUser;
    }

    public Long getsysDelUser() {
        return sysDelUser;
    }

    public void setsysDelUser(Long sysDelUser) {
        this.sysDelUser = sysDelUser;
    }

    public Integer getsysDelState() {
        return sysDelState;
    }

    public void setsysDelState(Integer sysDelState) {
        this.sysDelState = sysDelState;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", productName=" + productName +
        ", productId=" + productId +
        ", skuId=" + skuId +
        ", price=" + price +
        ", count=" + count +
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
