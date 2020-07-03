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

import net.bytebuddy.utility.RandomString;

@Entity
@Table(name="tbl_cupom_desconto")
public class CupomDesconto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="cupom_sequence")
	@SequenceGenerator(name = "cupom_sequence", sequenceName = "cup_seq", initialValue = 1001000, allocationSize = 1)
	@Column(name="cup_in_id")
	private Long id;

	@Column(name="cup_mo_valor")
	@NotNull(message="Informe o valor do desconto")
    private BigDecimal valor;
	
	@Column(name="cup_st_codigo",unique=true)
	@NotBlank(message="É obrigatória a inserção de um código para o desconto")
	private String codigo;
	
	@Column(name="cup_st_descricao")
	@NotBlank(message="É obrigatória a inserção de uma descrição para o cupom")
	private String descricao;
	
	@Column(name="cup_bo_ativo",columnDefinition = "boolean default 'true'")
	private Boolean ativo;
	
	@Column(name="cup_in_tipo")
	@NotNull
	@Enumerated(EnumType.ORDINAL)
    private TipoCupom tipoCupom;
	
	@Column(name="cup_in_id_cliente")
	private Integer idCliente;
	
	@Transient
	private String status;
	
	//Builder
	public CupomDesconto() {
		this.ativo = true;
	}
	
	//Construtor padrão para cupons de troca
	public CupomDesconto(Integer idCliente, BigDecimal valor) {
			
			this.ativo = true;
			this.idCliente = idCliente;
			this.tipoCupom = TipoCupom.TROCA;
			this.valor = valor;
			this.descricao = "Cupom gerado em derecorrência de solicitação de troca";
			this.codigo = geradorDeCodigoAleatorio();
			
	}
	
	//Construtor para cupons de troca com mensagens específicas
	public CupomDesconto(Integer idCliente, BigDecimal valor, String descricao) {
		
		this.ativo = true;
		this.idCliente = idCliente;
		this.tipoCupom = TipoCupom.TROCA;
		this.valor = valor;
		this.descricao = descricao;
		this.codigo = geradorDeCodigoAleatorio();
		
	}
	
	//Methods
	private static String geradorDeCodigoAleatorio() {
			
		String trechoAleatorio = RandomString.make(12).toUpperCase();
		return ("EXCHG" + trechoAleatorio);
	}

	//Getters and Setters
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public String getStatus() {
		if (ativo) {
			return "Ativo";
		}else {
			return "Inativo";
		}
	}

	public TipoCupom getTipoCupom() {
		return tipoCupom;
	}

	public void setTipoCupom(TipoCupom tipoCupom) {
		this.tipoCupom = tipoCupom;
	}

}
