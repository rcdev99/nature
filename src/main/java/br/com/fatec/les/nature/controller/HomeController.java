package br.com.fatec.les.nature.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value = "/nature/home", method = RequestMethod.GET)
	public String getIndex() {
		System.out.println("Entry point NaturÊ");
		return "index";
	}
}
