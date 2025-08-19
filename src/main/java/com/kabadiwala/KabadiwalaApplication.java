package com.kabadiwala;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KabadiwalaApplication {

	private static final Logger logger = LoggerFactory.getLogger(KabadiwalaApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(KabadiwalaApplication.class, args);
		logger.info("Server started successfully...");
	}

}
