package com.arthurh.portfolio.socialmedia;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ConfigurationPropertiesScan
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class SocialMediaApplication {

	static void main(String[] args) {
		SpringApplication.run(SocialMediaApplication.class, args);
	}

}
