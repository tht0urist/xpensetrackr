package io.obouh.backend.xpensetrackr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class XpensetrackrApplication {

	public static void main(String[] args) {
		SpringApplication.run(XpensetrackrApplication.class, args);
	}

}
