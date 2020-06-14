var cartoes = [];
var cartaoValido = false;
var endereco = new Object();
var enderecoValido = false;

function concluirCompra(){
	
	if(validarCartao() && validarEndereco()){
		
		var cartoesUtilizados = JSON.stringify(cartoes);
		var enderecoUtilizado = JSON.stringify(endereco);
		var idCliente = JSON.stringify(document.getElementById("idCliente").value);
		
		$.ajax({
		    url: '/rest/compra/concluir',
		    type: 'post',
		    data: {'cartoes': cartoesUtilizados,
		    	   'endereco': enderecoUtilizado,
		    	   'idCliente': idCliente
		    	},
		    success: function(result) {
		    	
		      console.log(result);
		      window.location = "/home";
		    }
		});
		
	}
}

function maisDeUmCartao(){

	adicionarCartao();
	maisDeUmCartao = true;
}

function adicionarCartao(){
	
	var cartao = new Object();
	var bandeira = document.getElementById("bandeira");
	
	cartao.idCliente = document.getElementById("idCliente").value;
	cartao.bandeira = bandeira.options[bandeira.selectedIndex].value;
	cartao.titular = document.getElementById("titular").value;
	cartao.numero = document.getElementById("numeroCartao").value;
	cartao.vencimento = document.getElementById("vencimento").value;
	cartao.cvv = document.getElementById("cvv").value;
	cartao.cobrar = document.getElementById("valor").value;
	
	if($("#save_card").is(':checked')){
		cartao.persistir = true;
	}else{
		cartao.persistir = false;
	}
	
	cartoes.push(cartao);
	
	inserirCartaoListagem(cartao);
}

function inserirCartaoListagem(cartao){
	
	var div = document.getElementById('cartoes_adicionados').innerHTML;	
	div = div + '<div id="card'+cartao.numero+'"><p class="d-flex" align="center"><span>...'+cartao.numero.substr(-4)+'</span> <span>R$ '+cartao.cobrar+'</span> <span>'+cartao.bandeira+'</span> <span><button type="button" class="btn btn-link" onclick="removeCartaoListagem('+cartao.numero+')" data-toggle="tooltip" data-placement="right" title="Remover cartão"><i class="icon-delete"></i></button></span></p></div>';
	document.getElementById('cartoes_adicionados').innerHTML = div;
	
	var total = document.getElementById("total_card");
	total.innerHTML = floatToText(obterValorCobrado());
	
}

function removeCartaoListagem(id){
	
	var node = document.getElementById("card"+id);
	  
		if (node.parentNode) {
			node.parentNode.removeChild(node);
		}
		
	retirarCartao(id);	
}

function retirarCartao(numero){
	
	for(var i = 0; i < cartoes.length; i++){
		if(cartoes[i].numero == numero){
			cartoes.splice(i,1);
			var total = document.getElementById("total_card");
			total.innerHTML = floatToText(obterValorCobrado());
		}
	}
	
}

function habilitarInserirCartao(){
	
	if($("#add_card").is(':checked')){
		document.getElementById("hidden-button-card").style.display = "block";
		document.getElementById("button-card").style.display = "none";
	}else{
		document.getElementById("hidden-button-card").style.display = "none";
		document.getElementById("button-card").style.display = "block";
	}
	
	habilitarValorCobrado();
}

function habilitarValorCobrado(){
	
	document.form_card.valor.disabled = (document.form_card.valor.disabled) ? 0 : 1;
	
	if(document.form_card.valor.disabled == 0){
		document.getElementById("valor").value = "";
		document.getElementById("valor").focus();
	}else{
		var total = textToFloat(document.getElementById("total").innerHTML);
		document.getElementById("valor").value = (total - obterValorCobrado());
	}
	
}

function validarValorCompra(){
	
	var totalCompra = textToFloat(document.getElementById("total").innerHTML);
	var totalCobrado = obterValorCobrado();
	
	if(totalCobrado == totalCompra){
		return true;
	}else {
		alert("Valor pendente: R$ " + (totalCompra - totalCobrado).toFixed(2));
		$("#collapseThree").slideDown();
		document.getElementById("valor").focus();
		return false;
	}
	
	
}

function validarValorInserido(valor){
	
	var valorInserido = parseFloat(valor);
	var totalCompra = textToFloat(document.getElementById("total").innerHTML);
	var valorCobrado = obterValorCobrado();
	
	valorTotal = valorInserido + valorCobrado;
	valorFaltante = (totalCompra - valorCobrado).toFixed(2);
	
	if(valorTotal > totalCompra){
		
		if(valorFaltante == 0){
			alert("O valor total da compra já foi contemplado");
			$("#collapseThree").slideUp();
			return false;
		}
		
		alert("O valor inserido excede o total da compra, falta apenas R$ " + valorFaltante);
		document.getElementById("valor").value = (totalCompra - valorCobrado).toFixed(2);
		document.getElementById("valor").focus();
		return false;
	}
	
	return true;
}

function obterValorCobrado(){
	
	var valor = 0.0;
	
	for(var i = 0; i < cartoes.length; i++){
		
		cobrado = textToFloat(cartoes[i].cobrar); 
		valor += cobrado;
	}
	
	return valor;
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
	    	
	      endereco = JSON.parse(result);
	      escreverEndereco(endereco)
	      enderecoValido = true;
	    }
	});
}

function habilitarOutroEndereco(){
	
	if($("#check_endereco").is(':checked')){
		document.getElementById("hidden-button-addres").style.display = "block";
		enderecoValido = false;
	}else{
		document.getElementById("hidden-button-addres").style.display = "none";
	}
	
	habilitarCampo();
	
}

function enderecoSelecionado(){
	
	if($("#check_endereco").is(':checked')){
		
		endereco.id = undefined;
		endereco.estado = document.getElementById("estado").value;
		endereco.cidade = document.getElementById("cidade").value;
		endereco.bairro = document.getElementById("bairro").value;
		endereco.logradouro = document.getElementById("logradouro").value;
		endereco.tipoResidencia = document.getElementById("tipoRes").value;
		endereco.numero = document.getElementById("numero").value;

	}
	
}

function escreverEndereco(endereco){
	
	document.getElementById("logradouro").value = endereco.logradouro;
	document.getElementById("numero").value = endereco.numero;
	document.getElementById("tipoRes").value = endereco.tipoResidencia;
	document.getElementById("cidade").value = endereco.cidade;
	document.getElementById("estado").value = endereco.estado;
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
	
	document.form_addres.logradouro.disabled = (document.form_addres.logradouro.disabled) ? 0 : 1;
	document.form_addres.numero.disabled = (document.form_addres.numero.disabled) ? 0 : 1;
	document.form_addres.tipoRes.disabled = (document.form_addres.tipoRes.disabled) ? 0 : 1;
	document.form_addres.cidade.disabled = (document.form_addres.cidade.disabled) ? 0 : 1;
	document.form_addres.estado.disabled = (document.form_addres.estado.disabled) ? 0 : 1;
	document.form_addres.bairro.disabled = (document.form_addres.bairro.disabled) ? 0 : 1;
	document.form_addres.pre_cadastrado.disabled = (document.form_addres.pre_cadastrado.disabled) ? 0 : 1;
	
	if(document.form_addres.logradouro.disabled == 1){
		obterEndereco();
	}else{
		limparCampos()
	}
}


/**
 * Validações
 */

function validarCartao(){
	
	if(cartoes.length <= 0){
		alert("Valide os dados do cartão para prosseguir com a compra");
		$("#collapseThree").slideDown();
		return false;
	}else{
		
		return validarValorCompra();
	}
	
}

function validarEndereco(){
	
	if(!enderecoValido){
		alert("Insira um endereço válido para a entrega");
		$("#collapseTwo").slideDown();
		return false;
	}
	
	return true;
}

/**
 * Funções para tratamento de texto
 */
function trataString(text) {
    var words = text.toLowerCase().split(" ");
    for (var a = 0; a < words.length; a++) {
        var w = words[a];
        words[a] = w[0].toUpperCase() + w.slice(1);
    }
    return words.join(" ");
}

function textToFloat(text){
	
	var valor = text.replace("R$ ", "").replace(",",".");
	valor = parseFloat(valor);
	return valor;
}

function floatToText(valor){
	
	var text = (valor < 1 ? "0" : "" ) + Math.floor(valor * 100);
	text = "R$ " + text;
	
	return text.substr(0, text.length - 2) + "," + text.substr(-2);
}

/**
 * Configurações relacionadas aos formulários
 */

//Desabilita submit do formulario de cartões
document.getElementById('form_card').onsubmit= function(e){
	e.preventDefault();
	
	var valor =  textToFloat(document.getElementById("valor").value);
	if (validarValorInserido(valor)){
		adicionarCartao();
		alert("Cartão válido !");
	}
	
}
//Desabilita submit do formulario de endereço
document.getElementById('form_addres').onsubmit= function(e){
	e.preventDefault();

	enderecoSelecionado()
	enderecoValido = true;
	alert("Endereço de entrega confirmado !");

}

/**
 * Executar a função assim que a tela for carregada
 */
window.onload = obterEndereco;