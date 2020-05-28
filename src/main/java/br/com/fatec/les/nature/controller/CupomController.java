package br.com.fatec.les.nature.controller;

import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fatec.les.nature.model.CupomDesconto;
import br.com.fatec.les.nature.model.TipoCupom;
import br.com.fatec.les.nature.service.CupomService;

@Controller
@RequestMapping("/cupom")
public class CupomController {

	@Autowired
	CupomService cService;
	
	
	@RequestMapping(value = "/listar")
	public ModelAndView listarCupons() {
		
		ModelAndView mView = new ModelAndView("dashboard-adm-cupons");
		
		List<CupomDesconto> cupons = cService.getAll();
		
		mView.addObject("cupons", cupons);
		
		return mView;
	}
	
	/**
	 * Método utilizado para direcionar o usuário administrativo à tela de cadastro de Cupons
	 * @param cupom Objeto para preenchimento dos campos caso tenha sifo feita tentativa inválida de cadastro
	 * @return mView View para cadastro de cupons.
	 */
	@RequestMapping(value = "/cadastro")
	public ModelAndView formCupom(CupomDesconto cupom) {
		
		ModelAndView mView = new ModelAndView("dashboard-adm-cupons-novo");
		
		mView.addObject("tiposCupom", TipoCupom.values());
		mView.addObject("cupom", cupom);
		
		return mView;
	}
	
	/**
	 * Método responsável pelo cadastro de novos cupons
	 * @param produto Produto a ser cadastrado
	 * @param result Resltado da valição
	 * @param redirectAttributes Mensagem de sucesso, caso os dados tenham sido inseridos corretamente
	 * @return mView View para listagem de produtos caso a validação ocorra, ou redicionamento para a mesma página em caso de erro.
	 */
	@RequestMapping(value = "/novo", method=RequestMethod.POST)
	public ModelAndView cadastrarCupom(@Valid CupomDesconto cupom, BindingResult result, RedirectAttributes redirectAttributes) throws SQLException, DataIntegrityViolationException {
		
		if(result.hasErrors()) {
			return formCupom(cupom);
			
		}
		
		ModelAndView mView = new ModelAndView("redirect:/cupom/listar");
		
		try {
			cService.salvar(cupom);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "Erro inesperado, tente novamente");
			return mView;
		}
		
		redirectAttributes.addFlashAttribute("mensagem", "Cupom cadastrado com sucesso !");
		mView.addObject("cupom", cupom);
		
		return mView;
	}
	
	/**
	 * Método utilizado para direcionar o usuário à view de Edição de cupons
	 * @param id Identificador único do cupom que sofrerá alterações
	 * @return
	 */
	@RequestMapping(value = "/editar/{cupom.id}")
	public ModelAndView editarCupom(@PathVariable("cupom.id") Long id) {
		
		ModelAndView mView = new ModelAndView("dashboard-adm-cupons-editar");
		
		CupomDesconto cupom = cService.findById(id);
		
		if(cupom == null) {
			 mView.setViewName("redirect:/cupom/listar"); 
			 return mView;
		}
		
		mView.addObject("cupom", cupom);
		mView.addObject("tiposCupom", TipoCupom.values());
				
		return mView;
	}
	
	/**
	 * Método responsável pela edição de cupons
	 * @param cupom Cupom a ser editado
	 * @param result Resultado da valição
	 * @param redirectAttributes Mensagem de sucesso, caso os dados tenham sido alterados corretamente
	 * @return mView View para listagem de cupons caso a validação ocorra, ou redicionamento para a mesma página em caso de erro.
	 */
	@RequestMapping(value = "/editar", method=RequestMethod.POST)
	public ModelAndView alterarProduto(@Valid CupomDesconto cupom, BindingResult result, RedirectAttributes redirectAttributes) {
		
		
		if(result.hasErrors()) {
			return formCupom(cupom);
		}
		
		ModelAndView mView = new ModelAndView("redirect:/cupom/listar");
		
		cService.salvar(cupom);
				
		redirectAttributes.addFlashAttribute("mensagem", "Cupom alterado com sucesso !");
		mView.addObject("cupom", cupom);
		
		return mView;
	}
	
	/**
	 * Método utilizado para excluir um cupom do DataBase
	 * @param id Identificador único do Cupom a ser excluído
	 * @param redirectAttributes Mensagem de conclusão da ação
	 * @return mView Tela de listagem de cupons
	 */
	@RequestMapping(value="/excluir/{cupom.id}")
	public ModelAndView excluirProduto(@PathVariable("cupom.id") Long id, RedirectAttributes redirectAttributes) {
		
		ModelAndView mView = new ModelAndView("redirect:/cupom/listar");
		
		cService.excluir(id);
		redirectAttributes.addFlashAttribute("delete", "Cupom excluido !");
		
		return mView;
	}
	
}
