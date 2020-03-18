package br.com.fatec.les.nature;

import br.com.fatec.les.nature.model.Usuario;
import br.com.fatec.les.nature.util.Criptografia;

public class TesteCriptografia {

	public static void main(String args[]) {
		
		Usuario usuario = new Usuario();
		Criptografia util = new Criptografia();
		
		usuario.setSenha("testaSenha");
		System.out.println("Senha criptografada: " + usuario.getSenha());
		
		if(usuario.getSenha().equals(util.criptografar("testaSenha"))) {
			System.out.println("Senha correta");
		}else {
			System.out.println("Senha incorreta!");	
		}
		
	}
	
}
