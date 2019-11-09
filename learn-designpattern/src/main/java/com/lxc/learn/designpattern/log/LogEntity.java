package com.lxc.learn.designpattern.log;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/9 12:01
 */
@Data
public class LogEntity {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    protected Long id;

    /**
     * 接口描述
     */
    protected String apiDesc;


    /**
     * 接口名称（boss）
     */
    protected String uipApiName;


    /**
     * 请求报文
     */
    protected String req;

    /**
     * 响应报文
     */
    protected String resp;


    /**
     * 响应状态码
     */
    protected String returnCode;


    /**
     * 请求开始时间
     */
    protected Date reqStartTime;

    /**
     * 请求结束时间
     */
    protected Date reqEndTime;

    /**
     * 请求耗时（毫秒）
     */
    protected Integer reqTotalTime;

    /**
     *
     */
    protected Date createdTime;

    /**
     *
     */
    protected Date updatedTime;

    /**
     *
     */
    protected Date deleteTime;

    /**
     *
     */
    protected Long createBy;

    /**
     *
     */
    protected Long updateBy;

    /**
     *
     */
    protected Long deleteBy;

    /**
     * 记录状态=={'1':'正常','2':'已删除'}
     */
    protected Integer deleteState;

}
