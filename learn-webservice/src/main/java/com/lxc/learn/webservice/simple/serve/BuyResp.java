package com.lxc.learn.webservice.simple.serve;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * @Auther: lixianchun
 * @Date: 2019/6/16 17:35
 * @Description:
 */
@Getter
@Setter
@ToString
/*@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BuyResp")*/
public class BuyResp implements Serializable {

    //@XmlElement(name = "totalMoney")
    private Long totalMoney;

    //@XmlElement(name = "code")
    private String code;

}
