package br.com.fatec.les.nature.dto;

import java.util.GregorianCalendar;

public class ComprasMensalDTO {

	private Integer qtdCompras;
	private GregorianCalendar mes;
	private String mesTxt;
	
	public ComprasMensalDTO() {
		
	}
	
	public ComprasMensalDTO(Integer qtd, Integer mes, Integer ano) {
		
		this.qtdCompras = qtd;
		this.mes = new GregorianCalendar(ano, (mes - 1), 1);
		
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
	
	public String getMesTxt() {
		
		switch (this.mes.get(GregorianCalendar.MONTH)) {
		case 0: {
			mesTxt = "Jan";
			break;
		}
		case 1: {
			mesTxt = "Fev";
			break;
		}
		case 2: {
			mesTxt = "Mar";
			break;
		}
		case 3: {
			mesTxt = "Abr";
			break;
		}
		case 4: {
			mesTxt = "Mai";
			break;
		}
		case 5: {
			mesTxt = "Jun";
			break;
		}
		case 6: {
			mesTxt = "Jul";
			break;
		}
		case 7: {
			mesTxt = "Ago";
			break;
		}
		case 8: {
			mesTxt = "Set";
			break;
		}
		case 9: {
			mesTxt = "Out";
			break;
		}
		case 10: {
			mesTxt = "Nov";
			break;
		}
		case 11: {
			mesTxt = "Dez";
			break;
		}
		default:
			throw new IllegalArgumentException("Valor inesperado: " + mes.get(GregorianCalendar.MONTH) + " insira valores entre 1 e 12");
		}
		
		return mesTxt +"/"+ this.mes.get(GregorianCalendar.YEAR);
	}

	public void setMesTxt(String mesTxt) {
		this.mesTxt = mesTxt;
	}
	
}
