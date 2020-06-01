function teste(){
	console.log("Heyé");
}

/**
 * Funcionalidades para alteração dinâmica do endereço
 */
function obterEndereco(){
	
	var box = document.getElementById("pre_cadastrado");
	var idEndereco = box.options[box.selectedIndex].value;
	
	//Requisiçao Ajax para envio dos dados da compra
	$.ajax({
	    url: '/rest/validar/endereco/' + idEndereco,
	    type: 'post',
	    data: JSON.stringify(idEndereco),
	    contentType: 'application/json',
	    success: function(result) {
	    	
	      var endereco = JSON.parse(result);	
	      console.log(endereco);
	      escreverEndereco(endereco)
	    }
	});
}

function escreverEndereco(endereco){
	
	document.getElementById("logradouro").value = endereco.logradouro.logradouro;
	document.getElementById("numero").value = endereco.numero;
	document.getElementById("tipoRes").value = trataString(endereco.tipoResidencia);
	document.getElementById("cidade").value = endereco.cidade.cidade;
	document.getElementById("estado").value = endereco.cidade.estado.sigla;
	document.getElementById("bairro").value = endereco.bairro;
}

function limparCampos(){
	
	document.getElementById("logradouro").value = "";
	document.getElementById("numero").value = "";
	document.getElementById("tipoRes").value = "";
	document.getElementById("cidade").value = "";
	document.getElementById("estado").value = "";
	document.getElementById("bairro").value = "";
	 
}

function habilitarCampo(){
	
	document.form.logradouro.disabled = (document.form.logradouro.disabled) ? 0 : 1;
	document.form.numero.disabled = (document.form.numero.disabled) ? 0 : 1;
	document.form.tipoRes.disabled = (document.form.tipoRes.disabled) ? 0 : 1;
	document.form.cidade.disabled = (document.form.cidade.disabled) ? 0 : 1;
	document.form.estado.disabled = (document.form.estado.disabled) ? 0 : 1;
	document.form.bairro.disabled = (document.form.bairro.disabled) ? 0 : 1;
	document.form.pre_cadastrado.disabled = (document.form.pre_cadastrado.disabled) ? 0 : 1;
	
	if(document.form.logradouro.disabled == 1){
		obterEndereco();
	}else{
		limparCampos()
	}
}

function trataString(text) {
    var words = text.toLowerCase().split(" ");
    for (var a = 0; a < words.length; a++) {
        var w = words[a];
        words[a] = w[0].toUpperCase() + w.slice(1);
    }
    return words.join(" ");
}

//Executar a função assim que a tela for carregada
window.onload = obterEndereco;