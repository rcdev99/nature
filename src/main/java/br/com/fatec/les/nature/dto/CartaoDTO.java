package br.com.fatec.les.nature.dto;

import java.math.BigDecimal;

public class CartaoDTO {

	private String idCliente;
	private String bandeira;
	private String titular;
	private String numero;
	private String vencimento;
	private String cvv;
	private BigDecimal cobrar;
	private boolean persistir;
	
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public String getBandeira() {
		return bandeira;
	}
	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}
	public String getTitular() {
		return titular;
	}
	public void setTitular(String titular) {
		this.titular = titular;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getVencimento() {
		return vencimento;
	}
	public void setVencimento(String vencimento) {
		this.vencimento = vencimento;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public BigDecimal getCobrar() {
		return cobrar;
	}
	public void setCobrar(BigDecimal cobrar) {
		this.cobrar = cobrar;
	}
	public boolean isPersistir() {
		return persistir;
	}
	public void setPersistir(boolean persistir) {
		this.persistir = persistir;
	}
}
