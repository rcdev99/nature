package br.com.fatec.les.nature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@ComponentScan({"br.com.fatec.les.nature.controller","br.com.fatec.les.nature.service","br.com.fatec.les.nature.storage"
	,"br.com.fatec.les.nature.conf","br.com.fatec.les.nature.dao","br.com.fatec.les.nature.model"})
public class NatureApplication {

	public static void main(String[] args) {
		SpringApplication.run(NatureApplication.class, args);
		
	}

} 
