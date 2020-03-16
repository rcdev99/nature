package br.com.fatec.les.nature.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Criptografia {

	public String criptografar(String senha) {
		
		String retorno = "";
		
		MessageDigest md;
		try {
			
			md = MessageDigest.getInstance("MD5");
			BigInteger hash = new BigInteger(1,md.digest(senha.getBytes()));
			retorno = hash.toString(16);
			
		} catch (Exception e) {
			System.out.println("Erro ao criptografar senha");
		}
		
		return retorno;
	}
	
}
