package com.lxc.learn.junit.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author li xian chun
 * @since 2020-04-17
 */
@TableName("user_extend")
public class UserExtend implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区域
     */
    private String area;
    /**
     * 详细地址
     */
    @TableField("detail_adress")
    private String detailAdress;
    /**
     * 个性签名
     */
    private String  signature;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDetailAdress() {
        return detailAdress;
    }

    public void setDetailAdress(String detailAdress) {
        this.detailAdress = detailAdress;
    }

    public String getSignature() {
        return  signature;
    }

    public void setSignature(String  signature) {
        this.signature =  signature;
    }

    @Override
    public String toString() {
        return "UserExtend{" +
        "id=" + id +
        ", userId=" + userId +
        ", province=" + province +
        ", city=" + city +
        ", area=" + area +
        ", detailAdress=" + detailAdress +
        ",  signature=" +  signature +
        "}";
    }
}
