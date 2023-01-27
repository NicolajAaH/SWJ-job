package dk.sdu.mmmi.jobservice.service.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages="dk.sdu.mmmi.jobservice")
@EntityScan("dk.sdu.mmmi.jobservice.service.model")
@EnableJpaRepositories("dk.sdu.mmmi.jobservice.outbound.repository")
public class JobserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobserviceApplication.class, args);
	}

}
