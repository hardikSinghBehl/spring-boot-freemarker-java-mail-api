package com.hardik.hedwig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringBootFreemarkerJavaMailApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFreemarkerJavaMailApiApplication.class, args);
	}

}
