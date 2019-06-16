package com.lxc.learn.webservice.simple.serve;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * @Auther: lixianchun
 * @Date: 2019/6/16 17:33
 * @Description:
 */
@Getter
@Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BuyReq",propOrder = {"name","address","id"})
@XmlRootElement(name = "BuyReq")
public class BuyReq implements Serializable {
   // private long serialVersionUID = 1;

    @XmlElement
    private String name;

    @XmlElement(name = "address")
    private String address;

    @XmlElement(name = "id")
    private Long id;


}
