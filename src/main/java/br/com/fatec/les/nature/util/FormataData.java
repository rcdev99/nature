package br.com.fatec.les.nature.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FormataData {

	
	
	public String exibeDataFormatada(Calendar data) {
	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data.getTime());
		
	}
}
