package br.com.fatec.les.nature.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormataData {

	
	/**
	 * Método que obtém um atributo do tipo LocalDate e retorna uma String formata para exibição da data.
	 * @param data
	 * @return
	 */
	public String exibeDataFormatada(LocalDate data) {
		
		DateTimeFormatter formato = getFormato(); 
		return formato.format(data);
		
	}
	
	/**
	 * Método que obtém uma String dentro dos parâmetros estabele em getFromato() e converte para um atributo do tipo LocalDate
	 * @param data String contendo a data que será instanciada
	 * @return LocalDate Atributo do tipo local Date estanciado com base na string passada como parâmetro
	 */
	public LocalDate formataData(String data) {
		
		DateTimeFormatter formato = getFormato(); 
		return LocalDate.parse(data, formato);
	
	}
	
	/**
	 * 
	 * @return DataTimeFormatter Contendo o formato de data que deverá ser utilizado na formatação.
	 */
	public DateTimeFormatter getFormato() {
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return formato;
	}
	/**
	 * 
	 * @return DataTimeFormatter Contendo o formato de data que deverá ser utilizado quando a requisição vier da view
	 */
	public DateTimeFormatter getFormatoView() {
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return formato;
	}
	
}
