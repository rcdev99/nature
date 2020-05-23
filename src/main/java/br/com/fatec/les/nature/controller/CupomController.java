package br.com.fatec.les.nature.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cupom")
public class CupomController {

	@RequestMapping(value = "/listar")
	public ModelAndView listarCupons() {
		
		ModelAndView mView = new ModelAndView("dashboard-adm-cupons");
		
		
		return mView;
	}
	
}
