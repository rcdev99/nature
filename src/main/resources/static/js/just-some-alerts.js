function mensagemEnviada(){
	
	var nome = document.getElementById("nome").value;
	var email = document.getElementById("email").value;
	var assunto = document.getElementById("assunto").value;
	var mensagem = document.getElementById("mensagem").value;
	
	if(nome.length > 3 && email.length > 5 && assunto.length > 3 && mensagem.length > 3){
		alert(nome + ", sua mensagem foi enviada !");
	}
}