package com.pg.helpdesk;

import io.springlets.format.config.EnableSpringletsEntityFormatWebSupport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableSpringletsEntityFormatWebSupport
@EnableScheduling
public class HelpdeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}
}
