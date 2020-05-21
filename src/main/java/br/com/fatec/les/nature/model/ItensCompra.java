package br.com.fatec.les.nature.model;

public class ItensCompra {

	private Produto produto;
	private Double quantidade;
	
	//Constructors
	public ItensCompra(Produto p, Double qtd) {
		
		this.produto = p;
		this.quantidade = qtd;	
	}
	
	public ItensCompra(Produto p) {
		
		this.produto = p;
		this.quantidade = 0.0;	
	}
	
	public ItensCompra() {
		
	}
	
	//Getters and Setters
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	
	//Methods
	public void incrementarQtdProduto() {

		this.quantidade = quantidade + 1;
	}
	
}
