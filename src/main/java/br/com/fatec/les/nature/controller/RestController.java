package br.com.fatec.les.nature.controller;


import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.fatec.les.nature.dao.EnderecoDAO;
import br.com.fatec.les.nature.dto.CartaoDTO;
import br.com.fatec.les.nature.dto.DuploRetornoDTO;
import br.com.fatec.les.nature.dto.EnderecoDTO;
import br.com.fatec.les.nature.dto.ItemCompraDTO;
import br.com.fatec.les.nature.model.Carrinho;
import br.com.fatec.les.nature.model.Carteira;
import br.com.fatec.les.nature.model.Compra;
import br.com.fatec.les.nature.model.CupomDesconto;
import br.com.fatec.les.nature.model.Endereco;
import br.com.fatec.les.nature.model.ItensCompra;
import br.com.fatec.les.nature.model.Produto;
import br.com.fatec.les.nature.model.SituacaoCompra;
import br.com.fatec.les.nature.model.Troca;
import br.com.fatec.les.nature.service.CompraService;
import br.com.fatec.les.nature.service.CupomService;
import br.com.fatec.les.nature.service.EstoqueService;
import br.com.fatec.les.nature.service.ProdutoService;
import br.com.fatec.les.nature.service.TrocaService;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {

	@Autowired
	CupomService cService;
	
	@Autowired
	ProdutoService pService;
	
	@Autowired
	CompraService compraService;
	
	@Autowired
	TrocaService trocaService;
	
	@Autowired
	EstoqueService estoqueService;
	
	@Autowired
	Carrinho carrinho;
	
	@Autowired
	Carteira carteira;
	
	EnderecoDAO DAOEndereco = new EnderecoDAO();
	
	Gson gson = new Gson();
	
	/**
	 * Método para validação de um cupom de desconto
	 * @param codigo - Identificar único do cupom
	 * @return Json contendo o cupom encontrado ou null caso o código não exista
	 */
	@RequestMapping(value = "/validar/cupom/{codigo}", method = RequestMethod.POST)
	public String validarCupom(@PathVariable("codigo") String codigo) {
		
		CupomDesconto cDesc = new CupomDesconto();
		cDesc = cService.findByCode(codigo);
		
		String json = gson.toJson(cDesc);
		
		return json;
	}
	
	/**
	 * Método para obtenção de um endereço com base em seu id
	 * @param idEndereco - Identificar único do endereço
	 * @return Json contendo o endereco encontrado ou null caso o código não exista
	 */
	@RequestMapping(value = "/validar/endereco/{idEndereco}", method = RequestMethod.POST)
	public String validarEndereco(@PathVariable("idEndereco") int idEndereco) {
		
		Endereco endereco = new Endereco();
		EnderecoDTO enderecoDTO = new EnderecoDTO();
		
		endereco = DAOEndereco.consultaById(idEndereco);
		enderecoDTO.construirDTOAtravesDeEndereco(endereco);
		
		String json = gson.toJson(enderecoDTO);
		
		return json;
	}
	
	
	/**
	 * Método responsável por preparar os dados iniciais da compra
	 * @param produtos Produtos a serem comprados
	 * @param cupons Possíveis cupons inseridos
	 * @return String de sucesso no processo inicial da compra
	 */
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public @ResponseBody String validarCompra(@RequestParam String produtos, @RequestParam String cupons) {
	
		ArrayList<ItensCompra> itensCompra = new ArrayList<ItensCompra>();
		
		Type itemType = new TypeToken<List<ItemCompraDTO>>() {}.getType();
		Type cupomType = new TypeToken<List<CupomDesconto>>() {}.getType();
		
		ArrayList<ItemCompraDTO> itens = gson.fromJson(produtos, itemType);
		ArrayList<CupomDesconto> cuponsCompra = gson.fromJson(cupons, cupomType);
		
		//Populando o array de itensCompra com os itens recebidos
		itensCompra = (ArrayList<ItensCompra>) obtemItens(itens);
		
		if(estoqueService.validarDisponibilidadeItens(itensCompra)) {
			
			//Adiciona os produtos e suas respectivas quantidades no carrinho
			carrinho.limparCarrinho();
			carrinho.setItens(itensCompra);
			//Adiciona os cupons do cliente à carteira
			carteira.esvaziarCarteira();
			carteira.setCupons(cuponsCompra);
			
			return gson.toJson(new DuploRetornoDTO(true, "OK"));
		}else {
			return gson.toJson(new DuploRetornoDTO(false, estoqueService.msgItensIndisponiveis(itensCompra)));
		}
	}
	
	@RequestMapping(value = "/atualizar/carrinho", method = RequestMethod.POST)
	public String atualizarQtdItensCarrinho(@RequestParam String produtos) {
		
		ArrayList<ItensCompra> itensCompra = new ArrayList<ItensCompra>();
		Type itemType = new TypeToken<List<ItemCompraDTO>>() {}.getType();
		ArrayList<ItemCompraDTO> itens = gson.fromJson(produtos, itemType);
		
		//Populando o array de itensCompra com os itens recebidos
		itensCompra = (ArrayList<ItensCompra>) obtemItens(itens);
		
		//Adiciona os produtos e suas respectivas quantidades no carrinho
		carrinho.limparCarrinho();
		carrinho.setItens(itensCompra);
		
		return"OK";
	}
	
	@RequestMapping(value = "/compra/concluir", method = RequestMethod.POST)
	public @ResponseBody String concluirCompra(@RequestParam String endereco, @RequestParam String cartoes, @RequestParam String idCliente) throws SQLException{
		
		Type enderecoType = new TypeToken<EnderecoDTO>() {}.getType();
		Type idClienteType = new TypeToken<Integer>() {}.getType();
		Type cartoesType = new TypeToken<List<CartaoDTO>>() {}.getType();
		
		EnderecoDTO endDto = gson.fromJson(endereco, enderecoType);
		Integer clienteId = gson.fromJson(idCliente, idClienteType);
		List<CartaoDTO> cartoesUtilizados = gson.fromJson(cartoes, cartoesType);
		
		System.out.println(cartoesUtilizados.size());
		
		String msgRetorno;
		
		Endereco end = new Endereco(endDto);
		end.setIdPessoa(clienteId);
		
		if(end.getId_endereco() == null) {
			DAOEndereco.salvar(end);
			end.setId_endereco(DAOEndereco.enderecoMaisRecente(clienteId).getId_endereco());
		}
		
		//Compondo compra
		Compra compra = new Compra();
		
		compra.setCupons(carteira.getCupons());
		compra.setItens(carrinho.getItens());
		compra.setTotal(carrinho.totalCompra(carteira.totalDescontos()));
		compra.setFrete(carrinho.getFrete());
		compra.setIdCliente(clienteId);
		compra.setIdEndereco(end.getId_endereco());
				
		if(estoqueService.validarDisponibilidadeItens(compra.getItens())){
			
			estoqueService.baixarItens(compra.getItens());
			msgRetorno = compraService.salvar(compra);
			
			//Valor da compra é inferior ao dos cupons utilizados ?
			if(carrinho.geraNovoCupom(carteira.totalDescontos())) {
				String descricao = "Cupom gerado em decorrência de resíduos do cupom utilizado em compras"; 
				CupomDesconto cupom = new CupomDesconto(compra.getIdCliente(), carrinho.valorNovoCupom(carteira.totalDescontos()), descricao);
				cService.salvar(cupom);
				cService.inativarCuponsDeTrocaUtilizados(carteira.getCupons());
			}
			
			carrinho.limparCarrinho();
			carteira.esvaziarCarteira();
			
			return msgRetorno;
		}else {
			return estoqueService.msgItensIndisponiveis(estoqueService.informarItensIndisponiveis(compra.getItens())); 
		}
	}
	
	@RequestMapping(value = "/solicitar/troca", method = RequestMethod.POST)
	public @ResponseBody String solicitarTroca(@RequestParam String itens, @RequestParam String id_compra, @RequestParam String motivo) {
		
		//Conversão dos dados recebidos
		Type itemType = new TypeToken<List<ItemCompraDTO>>() {}.getType();
		Type longType = new TypeToken<Long>() {}.getType();
		
		ArrayList<ItemCompraDTO> itensSelecionados = gson.fromJson(itens, itemType); 
		Long id = gson.fromJson(id_compra, longType);
		
		//Variáveis auxiliares
		String msg;
		
		//Instancia dos objetos necessários ao processo
		ArrayList<ItensCompra> itensTroca = new ArrayList<ItensCompra>();
		Compra compra = new Compra();
		compra = compraService.buscarCompraPorId(id);
		
		//Processo de troca
		itensTroca = (ArrayList<ItensCompra>) obtemItens(itensSelecionados);
		
		if(compra.validarTroca(itensTroca)) {
			
			Troca troca = new Troca(itensTroca, compra, motivo);
			
			trocaService.salvar(troca);
			compraService.salvar(compra);
			
			System.out.println(motivo);
			
			msg = "Solicitação de Troca efetuada com sucesso !";
		}else {
			msg = "Pedido de troca negado, para mais informações entre em contato com o administrador do sistema.";
		}
		
		return msg;
	}
	
	@RequestMapping(value="/add/item/{id.produto}", method = RequestMethod.POST)
	public String adicionarItemAoCarrinho(@PathVariable("id.produto") Long id) {
	
		Produto produto = new Produto();
		produto = pService.findById(id);
		
		if(produto != null) {
			ItensCompra item = new ItensCompra(produto);
			carrinho.adicionarItem(item);
		}
		
		Integer qtdProdutos = carrinho.getQtdProdutos(); 
		
		return qtdProdutos.toString();
	}
	
	@RequestMapping(value="/add/item/{id.produto}/{quantidade}", method = RequestMethod.POST)
	public String adicionarItemComQuantidade(@PathVariable("id.produto") Long id, @PathVariable("quantidade") Double qtd) {
	
		Produto produto = new Produto();
		produto = pService.findById(id);
		
		if(produto != null) {
			ItensCompra item = new ItensCompra(produto, qtd);
			carrinho.adicionarItem(item);
		}
		
		return "OK";
	}
	
	
	@RequestMapping(value = "/alterar/situacao/compra/{situacao}/{id}", method = RequestMethod.POST)
	public @ResponseBody String atualizarStatusPedido(@PathVariable SituacaoCompra situacao, @PathVariable Long id) {
		
		Compra compra = new Compra();
		compra = compraService.buscarCompraPorId(id);
		
		if(compra != null && situacao != null) {
			compra.setSituacao(situacao);
			compraService.salvar(compra);
			return "Status alterado com sucesso !";
		}else {
			return "Desculpe, houve um erro inesperado durante a operação";
		}
		
	}
	
	/**
	 * Método responsável por transformar uma lista de ItemCompraDTO em uma lista de ItensCompra
	 * @param itens Lista de ItemCompraDTO
	 * @return lista de ItensCompra
	 */
	public List<ItensCompra> obtemItens(List<ItemCompraDTO> itens){
		
		//Lista para conter os itens instanciados
		ArrayList<ItensCompra> itensCompra = new ArrayList<ItensCompra>();
		
		//Instanciando os itens da compra
		for (ItemCompraDTO itemCompra : itens) {
			//Variáveis de composição do Item da compra		
			Produto produto = new Produto();
			ItensCompra  item = new ItensCompra();
			
			//item possui id de produto ?
			if(itemCompra.getId() != null) {
				produto = pService.findById(itemCompra.getId());
			}
			
			//item foi encontrado ?
			if(produto != null) {
				item.setProduto(produto);
				item.setQuantidade(itemCompra.getQuantidade());
			
				itensCompra.add(item);
			}
		}
		
		return itensCompra;
	}
	
}
