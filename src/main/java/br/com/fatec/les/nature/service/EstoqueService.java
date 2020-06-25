package br.com.fatec.les.nature.service;

import java.math.BigDecimal;
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
	public boolean consultarEstoque(ItensCompra item) {
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
	 * Método responvável por efetuar a baixa na quantidade em estoque de um determinado produto
	 * @param item
	 * @return true caso baixa ocorra normalmente, ou false caso o produto não possua a quantidade solicitada disponível.
	 */
	public Boolean baixarEstoque(ItensCompra item) {
		//Quantidade disponível em estoque ?
		if(consultarEstoque(item)) {
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
