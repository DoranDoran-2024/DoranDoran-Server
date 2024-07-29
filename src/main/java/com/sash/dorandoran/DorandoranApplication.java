package com.sash.dorandoran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableFeignClients
@SpringBootApplication
public class DorandoranApplication {

	public static void main(String[] args) {
		SpringApplication.run(DorandoranApplication.class, args);
	}

}
