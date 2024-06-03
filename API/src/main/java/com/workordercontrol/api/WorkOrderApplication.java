package com.workordercontrol.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
		servers = {
				@Server(url = "http://localhost", description = "Default Server URL")
		},
		info = @Info(
					title = "Work Order API",
					description = "The applcation control workorders, storage and reserves to organize your business",
					contact = @Contact(name = "Finéias Antônio", url = "https://www.linkedin.com/in/fineias-antonio/", email = "fineias1187@gmail.com"),
					version = "1.0"
		)
)
public class WorkOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkOrderApplication.class, args);
	}

}
