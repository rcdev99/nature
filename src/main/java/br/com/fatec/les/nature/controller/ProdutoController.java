package br.com.fatec.les.nature.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fatec.les.nature.model.Produto;
import br.com.fatec.les.nature.model.TipoProduto;
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
	
	/**
	 * Método utilizado para direcionar o usuário administrativo à tela de cadastro de Produto
	 * @param produto Objeto para preenchimento dos campos caso tenha sifo feita tentativa inválida de cadastro
	 * @return mView View para cadastro de produtos.
	 */
	@RequestMapping(value = "/cadastro")
	public ModelAndView formProduto(Produto produto) {
		
		ModelAndView mView = new ModelAndView("dashboard-adm-produto-novo");
		
		
		mView.addObject("tiposProduto", TipoProduto.values());
		mView.addObject("produto", produto);
		
		return mView;
	}
	
	/**
	 * Método responsável pelo cadastro de novos produtos
	 * @param produto Produto a ser cadastrado
	 * @param result Resltado da valição
	 * @param redirectAttributes Mensagem de sucesso, caso os dados tenham sido inseridos corretamente
	 * @return mView View para listagem de produtos caso a validação ocorra, ou redicionamento para a mesma página em caso de erro.
	 */
	@RequestMapping(value = "/novo", method=RequestMethod.POST)
	public ModelAndView cadastrarProduto(@Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {
		
		
		if(result.hasErrors()) {
			return formProduto(produto);
		}
		
		ModelAndView mView = new ModelAndView("redirect:/produto/listar");
		
		pService.salvar(produto);
				
		redirectAttributes.addFlashAttribute("mensagem", "Produto cadastrado com sucesso !");
		mView.addObject("produto", produto);
		
		return mView;
	}

	/**
	 * Método utilizado para exibição dos detalhes de um determinado produto
	 * @param id Identificador único do produto a ser exibido
	 * @return mView View contendo os detalhes do produto
	 */
	@RequestMapping(value = "/detalhes/{produto.id}", method=RequestMethod.GET)
	public ModelAndView exibirDetalhes(@PathVariable("produto.id") Long id){		
	
		ModelAndView mView = new ModelAndView("dashboard-adm-produto-detalhes");
		Produto produto = new Produto();
		
		produto = pService.findById(id);
		
		mView.addObject("produto", produto);
		
		return mView;
	}
	
	@RequestMapping(value = "/editar/{produto.id}")
	public ModelAndView editarProduto(@PathVariable("produto.id") Long id) {
		
		ModelAndView mView = new ModelAndView("dashboard-adm-produto-editar");
		
		Produto produto = pService.findById(id);
		
		if(produto.temFoto()) {
			produto.setUrl("http://localhost:8080/fotos/" + produto.getFoto());
		}
		
		mView.addObject("produto", produto);
		mView.addObject("tiposProduto", TipoProduto.values());
		
		return mView;
	}
	
	/**
	 * Método responsável pela edição de produtos
	 * @param produto Produto a ser editado
	 * @param result Resultado da valição
	 * @param redirectAttributes Mensagem de sucesso, caso os dados tenham sido alterados corretamente
	 * @return mView View para listagem de produtos caso a validação ocorra, ou redicionamento para a mesma página em caso de erro.
	 */
	@RequestMapping(value = "/editar", method=RequestMethod.POST)
	public ModelAndView alterarProduto(@Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {
		
		
		if(result.hasErrors()) {
			return formProduto(produto);
		}
		
		ModelAndView mView = new ModelAndView("redirect:/produto/listar");
		
		pService.salvar(produto);
				
		redirectAttributes.addFlashAttribute("mensagem", "Produto alterado com sucesso !");
		mView.addObject("produto", produto);
		
		return mView;
	}
	
	/**
	 * Método utilizado para excluir um produto do DataBase
	 * @param id Identificador único do produto a ser excluído
	 * @param redirectAttributes Mensagem de conclusão da ação
	 * @return mView Tela de listagem de produtos
	 */
	@RequestMapping(value="/excluir/{produto.id}")
	public ModelAndView excluirProduto(@PathVariable("produto.id") Long id, RedirectAttributes redirectAttributes) {
		
		ModelAndView mView = new ModelAndView("redirect:/produto/listar");
		
		pService.excluir(id);
		redirectAttributes.addFlashAttribute("delete", "Item excluido !");
		
		return mView;
	}

}
