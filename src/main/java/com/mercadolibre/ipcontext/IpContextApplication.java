package com.mercadolibre.ipcontext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class IpContextApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpContextApplication.class, args);
	}

}
