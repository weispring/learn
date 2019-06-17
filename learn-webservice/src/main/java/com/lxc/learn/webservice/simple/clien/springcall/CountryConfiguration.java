
package com.lxc.learn.webservice.simple.clien.springcall;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CountryConfiguration {

	private String wsdl = "SimpleWebService.wsdl.xml";

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath(wsdl);
		return marshaller;
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
