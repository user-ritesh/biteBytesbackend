package com.ritesh.biteBytes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BiteBytesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiteBytesApplication.class, args);
	}

}
