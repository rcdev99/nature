package br.com.fatec.les.nature.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.fatec.les.nature.model.Produto;
import br.com.fatec.les.nature.model.TipoProduto;
import br.com.fatec.les.nature.repository.Produtos;
import br.com.fatec.les.nature.storage.FotoStorage;

@Repository
@Transactional
public class ProdutoService {

	@Autowired 
	private Produtos produtos;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Método responsável por persistir um produto no Data Base
	 * @param produto Objeto contendo as informações do produto a ser persistido
	 */
	public Produto salvar(Produto produto) {
		
		//Regras de Negócio
		return produtos.save(produto);
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
		
		Produto prod = new Produto();
		prod = produtos.findById(id).get();
		
		String url = obterUrl(prod);
		prod.setUrl(url);
		
		return prod; 
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
	
	/**
	 * Método utilizado para obter todos os produtos cadastrados e inserir a foto
	 * @return
	 */
	public List<Produto> getAllProducts(){
		
		List<Produto> allProducts = new ArrayList<Produto>();
				
		for (Produto product : produtos.findAll()) {
			
			String url = obterUrl(product);
			product.setUrl(url);
			allProducts.add(product);
			
		}
		
		return allProducts;
	}
	
	/**
	 * Método utilizado para realizar a contagem da quantidade de produtos cadastrados
	 * @return Long Quantidade de produtos cadastrados
	 */
	public Long quantidadeProdutosCadastrados() {
		return produtos.count();
	}
	
	public List<Produto> buscarPorCategoria(TipoProduto tipo){
		
		//Instancia de lista de produtos
		List<Produto> produtos = new ArrayList<Produto>();
		
		//Query para buscar os produtos por categoria
		TypedQuery<Produto> query = em.createQuery
				 ("SELECT p "
				+ "FROM Produto p "
				+ "WHERE p.tipo = :tipo "
				+ "ORDER BY p.nome desc ", Produto.class)
				 	.setParameter("tipo",tipo);
		
		//Loop para inclusão de url válida nas fotos
		for (Produto produto : query.getResultList()) {
			
			String url = obterUrl(produto);
			produto.setUrl(url);
			
			produtos.add(produto);
			
		}
		
		 return produtos; 
	}
	
	
	protected String obterUrl(Produto prod) {
		
		if(prod.temFoto()) {
			return fotoStorage.getUrl(prod.getFoto());
		}else {
			return "/images/mockup1.jpg";
		}
		
	}
	
}
