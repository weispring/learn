
package com.lxc.learn.webservice;

import com.lxc.learn.webservice.simple.clien.notuse.BuyResp;
import com.lxc.learn.webservice.simple.clien.springcall.SimplerClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

	/*@Bean
	CommandLineRunner lookup(SimplerClient client) {
		return args -> {
			String name = "Spain";
			if (args.length > 0) {
				name = args[0];
			}
			BuyResp response = client.simple(name);
			System.err.println(response);
		};
	}*/

}
