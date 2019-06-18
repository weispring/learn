
package com.lxc.learn.webservice.simple.clien.springcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
@Slf4j
public class WsConfiguration {

	//WARN this package must match the package in the <generatePackage> specified in pom.xml
	private String wsdl = "SimpleWebService.wsdl";

	@Bean
	public Jaxb2Marshaller marshaller() {
		try{
			Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

			marshaller.setContextPath(wsdl);
			return marshaller;
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return null;
	}

	@Bean
	public SimplerClient simplerClient(Jaxb2Marshaller marshaller) {
		SimplerClient client = new SimplerClient();
		client.setDefaultUri("http://localhost:9090/SimpleWebService");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

}
