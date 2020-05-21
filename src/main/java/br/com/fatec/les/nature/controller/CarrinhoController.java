package br.com.fatec.les.nature.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatec.les.nature.model.Carrinho;
import br.com.fatec.les.nature.model.ItensCompra;
import br.com.fatec.les.nature.model.Produto;
import br.com.fatec.les.nature.service.ProdutoService;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {


	@Autowired
	Carrinho carrinho;
	
	@Autowired
	ProdutoService pService;
	
	/**
	 * Método utilizado para direcionar o cliente ao carrinho de compras
	 * @return
	 */
	@RequestMapping()
	public ModelAndView carrinho() {
		
		ModelAndView mView = new ModelAndView("carrinho");
		
		mView.addObject("carrinho", carrinho);
		
		return mView;
		
	}
	
	/**
	 * Método utilizado para adicionar itens ao carrinho de compras
	 * @return
	 */
	@RequestMapping(value = "/add/{id}")
	public ModelAndView carrinho(@PathVariable("id") Long id) {
		
		ModelAndView mView = new ModelAndView("carrinho"); 
		
		Produto produto = pService.findById(id);
		ItensCompra item = new ItensCompra(produto);
		
		carrinho.adicionarItem(item);
		
		mView.addObject("carrinho", carrinho);
		
		return mView;
		
	}
	
	
}
