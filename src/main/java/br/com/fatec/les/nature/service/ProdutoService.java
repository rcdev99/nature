package br.com.fatec.les.nature.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.fatec.les.nature.model.Produto;
import br.com.fatec.les.nature.repository.Produtos;
import br.com.fatec.les.nature.storage.FotoStorage;

@Repository
@Transactional
public class ProdutoService {

	@Autowired 
	private Produtos produtos;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	/**
	 * Método responsável por persistir um produto no Data Base
	 * @param produto Objeto contendo as informações do produto a ser persistido
	 */
	public void salvar(Produto produto) {
		
		//Regras de Negócio
		produtos.save(produto);
	}
	
	/**
	 * Método responsável pela exclusão de um produto do Data Base
	 * @param id Identificador único do produto que será excluído
	 */
	public void excluir(Long id) {
		
		produtos.deleteById(id);
	}
	
	/**
	 * Método responsável por encontrar um produto no Data Base
	 * @param id Identificador único utilizado para obter o produto
	 * @return Produto correspondente ao id informado
	 */
	public Produto findById(long id) {
		
		return produtos.findById(id).get();
	}
	
	/**
	 * Método responsável por persistir o nome de uma foto de produto no Data Base 
	 * @param id Identificador único para associar a foto a um produto cadastrado
	 * @param nomeFoto String a ser persistida no Data Base contendo o nome da foto
	 */
	public String salvarFoto(Long id, MultipartFile foto) {
		String nomeFoto = fotoStorage.salvar(foto);
		
		Produto produto = produtos.findById(id).get();
		produto.setFoto(nomeFoto);
		produtos.save(produto);
		
		return fotoStorage.getUrl(nomeFoto);
		
	}
	
}
