package br.com.fatec.les.nature.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.les.nature.model.Produto;
import br.com.fatec.les.nature.repository.Produtos;

@Repository
@Transactional
public class ProdutoService {

	@Autowired 
	private Produtos produtos;
	
	public void salvar(Produto produto) {
		
		//Regras de Negócio
		produtos.save(produto);
	}
	
	public void excluir(Long id) {
		
		produtos.deleteById(id);
	}
	
	public Produto findById(long id) {
		
		return produtos.findById(id).get();
	}
	
}
