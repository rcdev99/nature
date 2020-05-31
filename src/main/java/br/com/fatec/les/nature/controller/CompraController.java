package br.com.fatec.les.nature.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.fatec.les.nature.service.CompraService;

@Controller
@RequestMapping("/compra")
public class CompraController {

	@Autowired
	CompraService comService;
	
}
