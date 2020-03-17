package br.com.fatec.les.nature.model;

import java.util.Calendar;

import br.com.fatec.les.nature.util.FormataData;

public class Cartao extends EntidadeDominio {

	private String titular;
	private String numCartao;
	private TipoBandeira bandeira;
	private Calendar dtVenc;
	private int cvv;
	
	//Re-escrita de métodos
	public String toString() {
		
		String string;
		
		string = "Titular: " + this.titular + "\nNº do Cartão: " + this.numCartao + " Bandeira: " + this.bandeira
				 +"\nData de Vencimento: " + exibeDtVenc() + " CVV: " + this.cvv;
		
		return string;
	}
	
	//Métodos
	public String exibeDtVenc() {
		
		FormataData fmdt = new FormataData();
		
		return fmdt.exibeDataFormatada(this.dtVenc);
	}
	
	
	//Getters and Setters
	public String getTitular() {
		return titular;
	}
	public void setTitular(String titular) {
		this.titular = titular;
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
	public Calendar getDtVenc() {
		return dtVenc;
	}
	public void setDtVenc(Calendar dtVenc) {
		this.dtVenc = dtVenc;
	}
	public int getCvv() {
		return cvv;
	}
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
	
}
