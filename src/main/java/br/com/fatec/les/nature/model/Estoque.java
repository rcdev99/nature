package br.com.fatec.les.nature.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tbl_estoque")
public class Estoque {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="estoque_sequence")
	@SequenceGenerator(name = "estoque_sequence", sequenceName = "est_seq", initialValue = 1, allocationSize = 1)
	@Column(name="est_in_id")
    private Long id;
	
	@OneToOne(cascade=CascadeType.ALL , fetch = FetchType.LAZY)
	@JoinColumn(name="est_in_id_produto", referencedColumnName = "pdt_in_id", unique=true)
	private Produto produto;
	
	@NotNull
	@Min(value=0)
	private BigDecimal quantidade;
	
	@Column(name="est_bo_disponivel" , columnDefinition = "boolean default 'true'")
	private boolean disponivel;
	
	@Transient
	private BigDecimal valorEmEstoque;
	
	@Transient
	private String disponibilidade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

	public BigDecimal getValorEmEstoque() {
		
		BigDecimal valor = new BigDecimal(0);
		
		valor = this.produto.getValor().multiply(quantidade);
		valor = valor.setScale(2, RoundingMode.HALF_EVEN);
		
		valorEmEstoque = valor;
		return valorEmEstoque;
	}

	public void setValorEmEstoque(BigDecimal valorEmEstoque) {
		this.valorEmEstoque = valorEmEstoque;
	}

	public String getDisponibilidade() {
		
		if(disponivel) {
			this.disponibilidade = "Disponível";
		}else {
			 this.disponibilidade = "Indisponível";
		}
		
		return disponibilidade;
	}

	public void setDisponibilidade(String disponibilidade) {
		this.disponibilidade = disponibilidade;
	}
	
}
