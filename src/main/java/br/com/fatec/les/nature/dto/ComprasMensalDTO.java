package br.com.fatec.les.nature.dto;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

public class ComprasMensalDTO {

	private Integer qtdCompras;
	private GregorianCalendar mes;
	private BigDecimal valor;
	
	public ComprasMensalDTO() {
		
	}
	
	public ComprasMensalDTO(Integer qtd, Integer mes, Integer ano, BigDecimal valor) {
		
		this.qtdCompras = qtd;
		this.valor = valor;
		this.mes = new GregorianCalendar(mes, ano, 1);
		
	}

	public Integer getQtdCompras() {
		return qtdCompras;
	}

	public void setQtdCompras(Integer qtdCompras) {
		this.qtdCompras = qtdCompras;
	}

	public GregorianCalendar getMes() {
		return mes;
	}

	public void setMes(GregorianCalendar mes) {
		this.mes = mes;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
}
