
package com.lxc.learn.webservice.simple.clien.springcall;

import SimpleWebService.wsdl.BuyReq;
import SimpleWebService.wsdl.BuyResp;
import SimpleWebService.wsdl.Simple;
import SimpleWebService.wsdl.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.support.MarshallingUtils;

public class SimplerClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(SimplerClient.class);

	public SimpleResponse simple(String name) {

		BuyReq request = new BuyReq();
		request.setName(name);
		Simple req = new Simple();
		req.setArg0(request);
		log.info("Requesting location for " + name);

		//ERROR 返回类型问题javax.xml.bind.JAXBElement
		//ERROR 生成的class文件缺少 @XmlRootElement
		Object response =  getWebServiceTemplate()
				.marshalSendAndReceive("http://localhost:9090/SimpleWebService", req,
						new SoapActionCallback(""));
		log.info("返回：{}",response);

		return (SimpleResponse) response;
	}

}
