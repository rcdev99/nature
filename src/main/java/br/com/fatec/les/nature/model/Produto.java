package br.com.fatec.les.nature.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tbl_produto")
public class Produto {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="produto_sequence")
	@SequenceGenerator(name = "produto_sequence", sequenceName = "prod_seq", initialValue = 300000, allocationSize = 1)
	@Column(name="pdt_id")
    private Long id;

	@Column(name="pdt_valor")
	@NotNull(message="Informe o valor do produto")
    private BigDecimal valor;
    
	@Column(name="pdt_nome")
	@NotBlank(message="É obrigatória a inserção de um nome para o produto")
	private String nome;
	
	@Column(name="pdt_descricao")
	@NotBlank(message="É obrigatória a inserção de uma descrição para o produto")
	private String descricao;
    
	@Column(name="pdt_tipo")
	@NotNull
	@Enumerated(EnumType.ORDINAL)
    private TipoProduto tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoProduto getTipo() {
		return tipo;
	}

	public void setTipo(TipoProduto tipo) {
		this.tipo = tipo;
	}
	
}
