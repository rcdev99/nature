package br.com.fatec.les.nature.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	/**
	 * Método reponsável por invocar a home page do sistema.
	 * @return String contendo o nome do arquivo html a ser invocado
	 */
	@RequestMapping(value = "/nature", method = RequestMethod.GET)
	public String getIndex() {
		System.out.println("Entry point NaturÊ");
		return "index";
	}
	
	/**
	 * Método responsável por incovar a página de exibição dos produtos do sistema. 
	 * @return
	 */
	@RequestMapping(value = "/produtos", method = RequestMethod.GET)
	public String mostrarProdutos(){
		
		return "produtos";
	}
	
	
}
