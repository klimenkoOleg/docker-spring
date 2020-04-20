package com.oklimenko.dockerspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class DockerSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerSpringApplication.class, args);
	}

}
