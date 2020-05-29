package br.com.fatec.les.nature.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import br.com.fatec.les.nature.model.CupomDesconto;
import br.com.fatec.les.nature.service.CupomService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {

	@Autowired
	CupomService cService;
	
	Gson gson = new Gson();
	
	@RequestMapping(value = "/validar/cupom/{codigo}", method = RequestMethod.POST)
	public String validarCupom(@PathVariable("codigo") String codigo) {
		
		CupomDesconto cDesc = new CupomDesconto();
		cDesc = cService.findByCode(codigo);
		
		String json = gson.toJson(cDesc);
		
		System.out.println(json);
		
		return json;
		
	}
	
	
}
