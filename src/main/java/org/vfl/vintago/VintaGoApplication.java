package org.vfl.vintago;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.vfl")
public class VintaGoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VintaGoApplication.class, args);
	}

}
