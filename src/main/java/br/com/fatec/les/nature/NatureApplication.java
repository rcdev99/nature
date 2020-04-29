package br.com.fatec.les.nature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"br.com.fatec.les.nature.controller","br.com.fatec.les.nature.service"})
public class NatureApplication {

	public static void main(String[] args) {
		SpringApplication.run(NatureApplication.class, args);
	}

}
