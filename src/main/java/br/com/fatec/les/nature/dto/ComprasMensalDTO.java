package br.com.fatec.les.nature.dto;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

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

	public ComprasMensalDTO(Integer qtd, String data) {
		
		String array[] = new String [2];
		array = data.split("/");
		
		Integer ano = Integer.valueOf(array[0]);
		Integer mes = Integer.valueOf(array[1]);
		
		this.mes = new GregorianCalendar(ano, (mes - 1), 1);
		this.qtdCompras = qtd;
	}
	
	/**
	 * Método que compõe a lista de período incluindo zero nos meses em que não houve registro de vendas dentro do período selecionado
	 * @param inicio - String que representa a data do início do período selecionado
	 * @param fim - String que representa a data do fim do período selecionado
	 * @param compras - List<ComprasMensalDTO> contendo as compras realizadas no período selecionado
	 * @return
	 */
	public static List<ComprasMensalDTO> comporListaPorPeriodo(String inicio, String fim, List<ComprasMensalDTO> compras){
		
		List<ComprasMensalDTO> comprasPorPeriodo = new ArrayList<ComprasMensalDTO>();
		
		//Array de string contendo: (0)-ano, (1)-mês, (2)-dia
		String begin[] = new String[3];
		String end[] = new String[3];
		
		begin = inicio.split("-");
		end = fim.split("-");
		
		GregorianCalendar dt_inicio = new GregorianCalendar(Integer.valueOf(begin[0]), (Integer.valueOf(begin[1]) - 1), Integer.valueOf(begin[2])); 
		GregorianCalendar dt_fim = new GregorianCalendar(Integer.valueOf(end[0]), (Integer.valueOf(end[1]) - 1), Integer.valueOf(end[2]));
		//Obs.: É necessário decrementar uma unidade do mês, pois o range de meses deste objeto vai de 0 a 11
		
		while(dt_fim.compareTo(dt_inicio) >= 0) {
			
			boolean encontrou = false;
			
			//Percorre a lista verificando se há no mês atual do iterador um registro correpondente na lista passada como parâmetro
			for (ComprasMensalDTO compra : compras) {
				if((compra.getMes().get(GregorianCalendar.YEAR) == dt_inicio.get(GregorianCalendar.YEAR)) 
						&& (compra.getMes().get(GregorianCalendar.MONTH) == dt_inicio.get(GregorianCalendar.MONTH))) {
					comprasPorPeriodo.add(compra);
					encontrou = true;
				}
			}
			
			//Caso não encontre, atribui qtd igual a zero àquele mês
			if(!encontrou) {
				comprasPorPeriodo.add(new ComprasMensalDTO(0, (dt_inicio.get(GregorianCalendar.MONTH) + 1), dt_inicio.get(GregorianCalendar.YEAR)));
			}
			
			//Incremento da data de início da busca
			dt_inicio.add(GregorianCalendar.MONTH, 1);
		}
	
		return comprasPorPeriodo;
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
