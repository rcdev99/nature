package br.com.fatec.les.nature.model;

import java.util.Calendar;

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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="tbl_cartao")
public class Cartao {

	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="cartao_sequence")
	@SequenceGenerator(name = "cartao_sequence", sequenceName = "card_seq", initialValue = 140000, allocationSize = 1)
	@Column(name="car_in_id")
    private Long id;
	
	@Column(name="car_st_titular")
	@NotBlank(message="É obrigatória a inserção do titular do cartão")
	private String titular;
	
	@Column(name="car_st_numero")
	@NotBlank(message="É obrigatória a inserção do número do cartão")
	private String numCartao;
	
	@Column(name="car_in_bandeira")
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private TipoBandeira bandeira;
	
	@Column(name="car_dt_vencimento")
	@NotNull
	@DateTimeFormat
    private Calendar dataVencimento;

	@Column(name="car_in_cvv")
	@NotNull
	private int cvv;
	
	@Column(name="car_in_cliente")
	@NotNull
	private Long idCliente;
	
	public Cartao(Long idCliente) {
		this.idCliente = idCliente;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getNumCartao() {
		return numCartao;
	}

	public void setNumCartao(String numCartao) {
		this.numCartao = numCartao;
	}

	public TipoBandeira getBandeira() {
		return bandeira;
	}

	public void setBandeira(TipoBandeira bandeira) {
		this.bandeira = bandeira;
	}

	public Calendar getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Calendar dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	
	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

}
