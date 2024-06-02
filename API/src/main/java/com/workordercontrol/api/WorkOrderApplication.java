package com.workordercontrol.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
		servers = {
				@Server(url = "http://localhost", description = "Default Server URL")
		}
)
public class WorkOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkOrderApplication.class, args);
	}

}
