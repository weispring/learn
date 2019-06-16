
package com.lxc.learn.webservice.simple.clien.notuse;

import com.lxc.learn.webservice.simple.serve.BuyReq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>simple complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="simple">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://serve.simple.webservice.learn.lxc.com/}buyReq" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "simple", propOrder = {
    "arg0"
})
public class Simple {

    protected com.lxc.learn.webservice.simple.serve.BuyReq arg0;

    /**
     * ��ȡarg0���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link com.lxc.learn.webservice.simple.serve.BuyReq }
     *     
     */
    public com.lxc.learn.webservice.simple.serve.BuyReq getArg0() {
        return arg0;
    }

    /**
     * ����arg0���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link com.lxc.learn.webservice.simple.serve.BuyReq }
     *     
     */
    public void setArg0(BuyReq value) {
        this.arg0 = value;
    }

}
