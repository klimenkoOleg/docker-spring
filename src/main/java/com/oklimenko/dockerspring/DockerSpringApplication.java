package com.oklimenko.dockerspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnablePrometheusMetrics
@SpringBootApplication
public class DockerSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerSpringApplication.class, args);
	}

}
