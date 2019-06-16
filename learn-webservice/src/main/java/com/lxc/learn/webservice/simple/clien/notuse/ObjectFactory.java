
package com.lxc.learn.webservice.simple.clien.notuse;

import com.lxc.learn.webservice.simple.serve.BuyReq;
import com.lxc.learn.webservice.simple.serve.BuyResp;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.lxc.learn.webservice.simple.serve package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Simple_QNAME = new QName("http://serve.simple.webservice.learn.lxc.com/", "simple");
    private final static QName _SimpleResponse_QNAME = new QName("http://serve.simple.webservice.learn.lxc.com/", "simpleResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.lxc.learn.webservice.simple.serve
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Simple }
     * 
     */
    public Simple createSimple() {
        return new Simple();
    }

    /**
     * Create an instance of {@link SimpleResponse }
     * 
     */
    public SimpleResponse createSimpleResponse() {
        return new SimpleResponse();
    }

    /**
     * Create an instance of {@link com.lxc.learn.webservice.simple.serve.BuyReq }
     * 
     */
    public com.lxc.learn.webservice.simple.serve.BuyReq createBuyReq() {
        return new BuyReq();
    }

    /**
     * Create an instance of {@link com.lxc.learn.webservice.simple.serve.BuyResp }
     * 
     */
    public com.lxc.learn.webservice.simple.serve.BuyResp createBuyResp() {
        return new BuyResp();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Simple }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serve.simple.webservice.learn.lxc.com/", name = "simple")
    public JAXBElement<Simple> createSimple(Simple value) {
        return new JAXBElement<Simple>(_Simple_QNAME, Simple.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SimpleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serve.simple.webservice.learn.lxc.com/", name = "simpleResponse")
    public JAXBElement<SimpleResponse> createSimpleResponse(SimpleResponse value) {
        return new JAXBElement<SimpleResponse>(_SimpleResponse_QNAME, SimpleResponse.class, null, value);
    }

}
