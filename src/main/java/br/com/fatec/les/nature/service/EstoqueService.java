package br.com.fatec.les.nature.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.fatec.les.nature.model.Estoque;
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
	 * Obtém o valor total dos protudos disponíveis em estoque
	 * @return Soma dos valores dos produtos disponíveis em estoque
	 */
	public BigDecimal valorTotalEmEstoque() {
		
		BigDecimal valor = new BigDecimal(0);
		
		for (Estoque estoque : estoqueRepository.findByDisponivel(true)) {
			valor = valor.add(estoque.getValorEmEstoque());
		}
		
		return valor;
	}
	
}
