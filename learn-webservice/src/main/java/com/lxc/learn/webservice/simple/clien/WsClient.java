package com.lxc.learn.webservice.simple.clien;

import com.lxc.learn.webservice.simple.clien.notuse.SimpleWebServiceService;
import com.lxc.learn.webservice.simple.serve.BuyReq;
import com.lxc.learn.webservice.simple.serve.BuyResp;
import com.sun.xml.internal.bind.v2.runtime.MarshallerImpl;
import com.sun.xml.internal.ws.util.JAXWSUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.StringWriter;
import java.net.URL;

/**
 * @Auther: lixianchun
 * @Date: 2019/6/16 15:00
 * @Description:
 */
public class WsClient {
    public static void main(String[] args) throws Exception{
        //创建一个连接（地址对应的就是webservice服务器中的地址）
        URL wsdlDocumentLocation = new URL("http://localhost:9090/SimpleWebService?wsdl");
        //这个参数，主要是根据我们设定的webService的接口来进行的
        QName serviceName = new QName("http://serve.simple.webservice.learn.lxc.com/","SimpleWebServiceService");
        Service ws = Service.create(wsdlDocumentLocation , serviceName );
        //SimpleWebService  port = ws.getPort(SimpleWebService.class);
        //获取到调用的对象内容
        SimpleWebService port =  new SimpleWebServiceService().getSimpleWebServicePort();

        //方法的调用
        BuyReq req = new BuyReq();
        req.setAddress("地址");
        req.setName("apple");
        //BuyResp result = port.simple(toXML(req));
        BuyResp result = port.simple(req);
        System.out.println(result);
    }


    public static String toXML(Object obj) throws Exception {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        //marshaller.setListener(new MarshallerImpl());
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// //编码格式
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xm头声明信息
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        String xml = writer.toString();
        return xml;
    }

}
