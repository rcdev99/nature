package br.com.fatec.les.nature.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.springframework.security.crypto.password.PasswordEncoder;

public class Criptografia implements PasswordEncoder {

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

	@Override
	public String encode(CharSequence rawPassword) {
		
		String string = (String) rawPassword;
		return criptografar(string);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		
		String senhaCrua;
		
		senhaCrua = encode(rawPassword);
		
		if(senhaCrua.equals(encodedPassword)) {
			return true;
		}else {
			return false;
		}
		
	}
	
}
