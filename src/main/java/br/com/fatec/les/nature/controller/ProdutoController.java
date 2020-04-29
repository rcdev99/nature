package br.com.fatec.les.nature.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.fatec.les.nature.model.Produto;
import br.com.fatec.les.nature.repository.Produtos;
import br.com.fatec.les.nature.service.ProdutoService;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	Produtos produtoRepository;
	
	@Autowired
	ProdutoService pService;
	
	/**
	 * Método utilizado para direcionar o usuário administrativo ao painel de controle de produtos
	 * @return
	 */
	@RequestMapping(value = "/listar")
	public ModelAndView getProdutos() {
		
		//Variavéis locais
		ModelAndView mView = new ModelAndView("dashboard-adm-produtos");
	
		List<Produto> produtos = produtoRepository.findAll();
		
		mView.addObject("produtos", produtos);
		
		return mView;
	}
	
	@RequestMapping(value = "/detalhes/{produto.id}", method=RequestMethod.GET)
	public ModelAndView exibirDetalhes(@PathVariable("produto.id") Long id){		
	
		ModelAndView mView = new ModelAndView("dashboard-adm-produto-detalhes");
		Produto produto = new Produto();
		
		produto = pService.findById(id);
		
		mView.addObject("produto", produto);
		
		return mView;
	}
	
}
