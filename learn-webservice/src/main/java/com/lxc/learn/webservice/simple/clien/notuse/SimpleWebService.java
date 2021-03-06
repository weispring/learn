
package com.lxc.learn.webservice.simple.clien.notuse;

import com.lxc.learn.webservice.simple.serve.BuyReq;
import com.lxc.learn.webservice.simple.serve.BuyResp;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SimpleWebService", targetNamespace = "http://serve.simple.webservice.learn.lxc.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SimpleWebService {


    /**
     * 
     * @param arg0
     * @return
     *     returns com.lxc.learn.webservice.simple.serve.BuyResp
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "simple", targetNamespace = "http://serve.simple.webservice.learn.lxc.com/", className = "com.lxc.learn.webservice.simple.serve.Simple")
    @ResponseWrapper(localName = "simpleResponse", targetNamespace = "http://serve.simple.webservice.learn.lxc.com/", className = "com.lxc.learn.webservice.simple.serve.SimpleResponse")
    @Action(input = "http://serve.simple.webservice.learn.lxc.com/SimpleWebService/simpleRequest", output = "http://serve.simple.webservice.learn.lxc.com/SimpleWebService/simpleResponse")
    public BuyResp simple(
            @WebParam(name = "arg0", targetNamespace = "")
                    BuyReq arg0);

}
