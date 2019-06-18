
package com.lxc.learn.webservice.simple.clien.springcall;

import SimpleWebService.wsdl.BuyReq;
import SimpleWebService.wsdl.BuyResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class SimplerClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(SimplerClient.class);

	public BuyResp simple(String name) {

		BuyReq request = new BuyReq();
		request.setName(name);

		log.info("Requesting location for " + name);

		BuyResp response = (BuyResp) getWebServiceTemplate()
				.marshalSendAndReceive("http://localhost:9090/SimpleWebService", request,
						new SoapActionCallback(
								"http://serve.simple.webservice.learn.lxc.com/SimpleWebService/simpleRequest"));
		log.info("返回：{}",response);
		return response;
	}

}
