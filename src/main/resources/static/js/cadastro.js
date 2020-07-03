function validarSenha(){
	
	var senha = document.getElementById("senha").value;
	var regex = /^(?=(?:.*?[A-Z]){0})(?=(?:.*?[0-9]){1})(?=(?:.*?[!@#$%*()_+^&}{:;?.]){0})(?!.*\s)[0-9a-zA-Z!@#$%;*(){}_+^&]*$/;
	
	if(senha != null && senha!="" && !(senha.match(/^\s+$/)) && senha.length >= 8 && regex.test(senha)){
		
		var div = document.getElementById('check-senha').innerHTML;
		div = '<span class="oi oi-circle-check" style="color: green"></span>';
		document.getElementById('check-senha').innerHTML = div;
		return true;
	}else{
		
		var div = document.getElementById('check-senha').innerHTML;
		div = '<span class="oi oi-circle-x" style="color: red"></span>';
		document.getElementById('check-senha').innerHTML = div;
		return false;
	}
}

function confirmarSenha(){
	
	var senha = document.getElementById("senha").value;
	var confirmacao = document.getElementById("conf_senha").value;
	
	if(validarSenha() && (senha === confirmacao)){
		
		var div = document.getElementById('check-confirmar-senha').innerHTML;
		div = '<span class="oi oi-circle-check" style="color: green"></span>';
		document.getElementById('check-confirmar-senha').innerHTML = div;
	}else{
		
		var div = document.getElementById('check-confirmar-senha').innerHTML;
		div = '<span class="oi oi-circle-x" style="color: red"></span>';
		document.getElementById('check-confirmar-senha').innerHTML = div;
	}
	
}

document.getElementById('form-cadastro').onsubmit= function(e){
	
	//confirmou a senha ?
	if(!validarSenha() || !confirmarSenha()){
		
		alert("Preencha corretamente o campo senha");
		document.getElementById("senha").focus();
		e.preventDefault();
	}
	
}