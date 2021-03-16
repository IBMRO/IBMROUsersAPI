package com.ibm.ro.users;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableEurekaClient
public class IbmroUsersApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbmroUsersApiApplication.class, args);
	}

	@Bean
	public ModelMapper mm() {
		return new ModelMapper();
	}

	@Bean
	public BCryptPasswordEncoder bcr() {
		return new BCryptPasswordEncoder();
	}
}
