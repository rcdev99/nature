package br.com.fatec.les.nature.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fatec.les.nature.model.Estoque;
import br.com.fatec.les.nature.service.EstoqueService;

@Controller
@RequestMapping("/estoque")
public class EstoqueController {

	@Autowired
	EstoqueService estoqueService;
	
	@RequestMapping(value="/listar")
	public ModelAndView listarEstoque() {
		
		ModelAndView mView = new ModelAndView("dashboard-adm-estoque");
		mView.addObject("estoque", estoqueService.buscarTodos());
		mView.addObject("valorTotal", estoqueService.valorTotalEmEstoque());
		
		return mView;
	}
	
	@RequestMapping(value="/gerenciar/{id.estoque}")
	public ModelAndView alterarEstoque(@PathVariable("id.estoque") Long id, RedirectAttributes redirectAttributes){
		
		ModelAndView mView = new ModelAndView("dashboard-adm-estoque-detalhes");
		
		Estoque estoque = estoqueService.buscarPorId(id);
		mView.addObject("estoque", estoque);
		
		
		
		return mView;
	}
	
}
