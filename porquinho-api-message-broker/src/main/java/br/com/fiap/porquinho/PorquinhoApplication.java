package br.com.fiap.porquinho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PorquinhoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PorquinhoApplication.class, args);
	}

}
