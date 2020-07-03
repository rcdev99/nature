package br.com.fatec.les.nature.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Embeddable
public class ItensCompra {

	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	private Produto produto;
	
	@NotNull
	@Column(name="coi_com_in_quantidade")
	private Double quantidade;
	
	@Transient
	private BigDecimal total;
	
	//Constructors
	public ItensCompra(Produto p, Double qtd) {
		
		this.produto = p;
		this.quantidade = qtd;	
	}
	
	public ItensCompra(Produto p) {
		
		this.produto = p;
		this.quantidade = 1.0;	
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
	public BigDecimal getTotal() {
		
		BigDecimal quantidade = BigDecimal.valueOf(this.quantidade);
		
		total = this.produto.getValor().multiply(quantidade);		
		total = total.setScale(2, RoundingMode.HALF_EVEN);
		
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	//Methods
	public void incrementarQtdProduto() {

		this.quantidade = quantidade + 1;
	}
	
}
