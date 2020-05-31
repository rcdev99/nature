package br.com.fatec.les.nature.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Embeddable
public class ItensCompra {

	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	private Produto produto;
	
	@NotNull
	@Column(name="coi_com_in_quantidade")
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
