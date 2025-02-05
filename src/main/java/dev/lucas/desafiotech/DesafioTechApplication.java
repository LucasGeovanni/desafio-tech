package dev.lucas.desafiotech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAspectJAutoProxy
@EnableFeignClients
@EnableScheduling
@SpringBootApplication
@EnableRetry
public class DesafioTechApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioTechApplication.class, args);
	}

}
