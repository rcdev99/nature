package br.com.fatec.les.nature.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.fatec.les.nature.model.Estoque;
import br.com.fatec.les.nature.model.ItensCompra;
import br.com.fatec.les.nature.model.Produto;
import br.com.fatec.les.nature.repository.EstoqueRepository;

@Repository
@Transactional
public class EstoqueService {

	@Autowired 
	EstoqueRepository estoqueRepository;
	
	public void salvar(Estoque estoque) {
		
		if(estoque.getQuantidade().compareTo(new BigDecimal(0)) <= 0) {
			estoque.setQuantidade(new BigDecimal(0));
			estoque.setDisponivel(false);
		}
		estoqueRepository.save(estoque);
	}
	
	public void excluir(Estoque estoque) {
		estoqueRepository.delete(estoque);
	}
	
	public Estoque buscarPorId(Long id) {
		return estoqueRepository.findById(id).get();
	}
	
	public List<Estoque> buscarTodos(){
		return estoqueRepository.findAll();
	}
	
	public List<Estoque> buscarPorSituacao(boolean situacao){
		return estoqueRepository.findByDisponivel(situacao);
	}
	
	/**
	 * Método para obter os valores de estoque com base em um determinado produto
	 * @param produto
	 * @return Estoque Retorna o estoque referente a um determinado produto
	 */
	public Estoque buscarPorProduto(Produto produto) {
		return estoqueRepository.findByProduto(produto);
	}
	
	/**
	 * Método responsável por verificar se uma determinada quantidade de um dado produto encontra-se disponível em estoque
	 * @param item
	 * @return true caso disponível ou false caso a quantidade seja indisponível ou haja erro de preenchimento do item informado.
	 */
	public boolean disponivelEmEstoque(ItensCompra item) {
		//Item preenchido ?
		if(item != null) {
			//Instancia de estoque baseada no Item da compra
			Estoque estoque = new Estoque();
			estoque = estoqueRepository.findByProduto(item.getProduto());
			//Encontrou estoque ?
			if(estoque != null) {
				//Conversão da quantidade solicitada para BigDecimal 
				BigDecimal qtdSolicitada = new BigDecimal(item.getQuantidade());
				//Quantidade disponível
				if(estoque.getQuantidade().compareTo(qtdSolicitada) >= 0) {
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * Método responsável por realizar a reposição de estoque
	 * @param idEstoque Identificador referente ao estoquea ser reposto
	 * @param qtd Quantidade a ser adicionada em estoque
	 */
	public void reporEstoque(Long idEstoque, BigDecimal qtd) {
		
		Estoque estoque = estoqueRepository.findById(idEstoque).get();
		
		if(estoque != null) {
			if(qtd.compareTo(new BigDecimal(0)) > 0) {
				estoque.setQuantidade(estoque.getQuantidade().add(qtd));
				estoqueRepository.save(estoque);
			}
		} 
	}
	
	/**
	 * Método responvável por efetuar a baixa na quantidade em estoque de um determinado produto
	 * @param item
	 * @return true caso baixa ocorra normalmente, ou false caso o produto não possua a quantidade solicitada disponível.
	 */
	public Boolean baixarEstoque(ItensCompra item) {
		//Quantidade disponível em estoque ?
		if(disponivelEmEstoque(item)) {
			Estoque estoque = estoqueRepository.findByProduto(item.getProduto());
			BigDecimal qtdRestante = estoque.getQuantidade().subtract(new BigDecimal(item.getQuantidade()));
			//Quantidade que temos cobre a quantidade solicitada ?
			if(qtdRestante.compareTo(new BigDecimal(0)) >= 0) {
				estoque.setQuantidade(qtdRestante);
				//Precaução contra requisições simultâneas
				try {
					estoqueRepository.save(estoque);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				} 
				return true;
			}	
		}
		
		return false;
	}
	
	/**
	 * Método utilizado para percorrer um lista de ItensCompra e identificar se algum deles não está disponível em estoque
	 * @param itens
	 * @return boolean - true caso todos os itens estejam disponíveis ou false no caso de um dele não contar com a quantidade solicitada disponível
	 */
	public boolean validarDisponibilidadeItens(List<ItensCompra> itens) {
		
		for (ItensCompra item: itens) {
			if(!disponivelEmEstoque(item)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Percorre uma lista de ItensCompra verificando se há algum item indisponível, caso encontre adiciona o item ao retorno
	 * @param itens - Lista de ItensCompra a ser verificada
	 * @return List<ItensCompra> Contendo os itens que não estão disponíveis na quantidade informada
	 */
	public List<ItensCompra> informarItensIndisponiveis(List<ItensCompra> itens){
		
		List<ItensCompra> itensIndisponiveis = new ArrayList<ItensCompra>();
		
		for (ItensCompra item : itens) {
			if(!disponivelEmEstoque(item)){
				itensIndisponiveis.add(item);
			}
		}
		
		return itensIndisponiveis;
	}
	
	public String msgItensIndisponiveis(List<ItensCompra> itensIndisponiveis) {
		
		String inicio;
		String meio = "";
		String fim;
		
		if(itensIndisponiveis.size() > 1) {
			inicio = "Os seguintes itens: \n";
			fim = "Encontram-se indisponíveis na quantidade solicitada";
		}else {
			inicio = "O seguinte item: \n";
			fim = "Encontra-se indisponível na quantidade solicitada";
		}
		
		for (ItensCompra item : itensIndisponiveis) {
			meio += (". " + item.getProduto().getNome() + "\n");
		}
		
		return (inicio + meio + fim);
	}
	
	/**
	 * Método responsável por efetuar a baixa de uma lista de ItensCompra
	 * @param itens Lista de Itens a serem retirados do estoque
	 */
	public void baixarItens(List<ItensCompra> itens) {
		for (ItensCompra item : itens) {
			baixarEstoque(item);
		}
	}
	
	/**
	 * Obtém o valor total dos protudos disponíveis em estoque
	 * @return BigDecimal valor - Resultado da Soma dos valores dos produtos disponíveis em estoque
	 */
	public BigDecimal valorTotalEmEstoque() {
		
		BigDecimal valor = new BigDecimal(0);
		
		for (Estoque estoque : estoqueRepository.findByDisponivel(true)) {
			valor = valor.add(estoque.getValorEmEstoque());
		}
		
		return valor;
	}
	
}
