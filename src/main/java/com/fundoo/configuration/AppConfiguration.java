package com.fundoo.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.fundoo.service.EmailService;
import com.fundoo.util.Response;

@Configuration
public class AppConfiguration {

	//Password Encoder configuration bean
	@Bean
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public EmailService emailService() {
		return new EmailService();
	}

	@Bean
	public Response response() {
		return new Response();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
