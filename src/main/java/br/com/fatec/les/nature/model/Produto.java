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
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

@Entity
@Table(name="tbl_produto")
public class Produto {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="produto_sequence")
	@SequenceGenerator(name = "produto_sequence", sequenceName = "prod_seq", initialValue = 300000, allocationSize = 1)
	@Column(name="pdt_in_id")
    private Long id;

	@Column(name="pdt_mo_valor")
	@NotNull(message="Informe o valor do produto")
    private BigDecimal valor;
    
	@Column(name="pdt_st_nome")
	@NotBlank(message="É obrigatória a inserção de um nome para o produto")
	private String nome;
	
	@Column(name="pdt_st_descricao")
	@NotBlank(message="É obrigatória a inserção de uma descrição para o produto")
	private String descricao;
    
	@Column(name="pdt_in_tipo")
	@NotNull
	@Enumerated(EnumType.ORDINAL)
    private TipoProduto tipo;
	
	@Column(name="pdt_st_foto")
	private String foto;
	
	@Column(name="pdt_in_tipo_preco")
	@NotNull
	@Enumerated(EnumType.ORDINAL)
    private TipoPrecificacao tipoPreco;
	
	@Transient
	private String url;
	
	@Transient
	private Double step;
	
	//Getters and Setters
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFoto() {
		return foto;
	}
	
	public void setFoto(String foto) {
		this.foto = foto;
	}

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
	
	public TipoPrecificacao getTipoPreco() {
		return tipoPreco;
	}

	public void setTipoPreco(TipoPrecificacao tipoPreco) {
		this.tipoPreco = tipoPreco;
	}
	
	public Double getStep() {
		
		if(this.tipoPreco.equals(TipoPrecificacao.LITRO) || this.tipoPreco.equals(TipoPrecificacao.QUILOGRAMA)) {
			this.step = 0.10;
		}else {
			this.step = 1.0;
		}
		
		return step;
	}

	public void setStep(Double step) {
		this.step = step;
	}
	
	//Métodos
	public Boolean temFoto() {
		return !StringUtils.isEmpty(this.foto);
	} 
	
}
