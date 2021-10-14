package br.com.nadsen.desafioeveris;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DesafioEverisApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioEverisApplication.class, args);
	}

}
