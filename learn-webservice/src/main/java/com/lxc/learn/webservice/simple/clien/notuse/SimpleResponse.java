
package com.lxc.learn.webservice.simple.clien.notuse;

import com.lxc.learn.webservice.simple.serve.BuyResp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>simpleResponse complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="simpleResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://serve.simple.webservice.learn.lxc.com/}buyResp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "simpleResponse", propOrder = {
    "_return"
})
public class SimpleResponse {

    @XmlElement(name = "return")
    protected com.lxc.learn.webservice.simple.serve.BuyResp _return;

    /**
     * ��ȡreturn���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link com.lxc.learn.webservice.simple.serve.BuyResp }
     *     
     */
    public com.lxc.learn.webservice.simple.serve.BuyResp getReturn() {
        return _return;
    }

    /**
     * ����return���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link com.lxc.learn.webservice.simple.serve.BuyResp }
     *     
     */
    public void setReturn(BuyResp value) {
        this._return = value;
    }

}
