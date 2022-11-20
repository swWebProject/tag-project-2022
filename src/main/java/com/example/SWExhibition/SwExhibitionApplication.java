package com.example.SWExhibition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing	// EntityListeners 활성
@EnableScheduling	// Scheduler 활성
@SpringBootApplication
public class SwExhibitionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwExhibitionApplication.class, args);
	}

}
