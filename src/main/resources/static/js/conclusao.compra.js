var endereco = new Object();
var cartoes = [];

document.getElementById('form_card').onsubmit= function(e){
    e.preventDefault();
}


function teste(){
	console.log("Heyé");
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
	
	console.log(endereco);
	
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
	
	console.log(cartao);
	cartoes.push(cartao);
	
}

function fracionarValor(){
	
	
	
}

function habilitarValorCobrado(){
	
	document.form_card.valor.disabled = (document.form_card.valor.disabled) ? 0 : 1;
	
	if(document.form_card.valor.disabled == 0){
		alert("Insira o valor a ser pago com este cartão");
		document.getElementById("valor").value = "";
	}else{
		var total = textToFloat(document.getElementById("total").innerHTML);
		console.log(total);
		document.getElementById("valor").value = (total - obterValorCobrado());
	}
	
}

function validarValorInserido(valor){
	
	var totalCompra = textToFloat(document.getElementById("total").innerHTML);
	
	if((valor + obterValorCobrado()) > totalCompra){
		alert("O valor inserido excede o total da compra em: R$ " + ((valor + obterValorCobrado()) - totalCompra).toFixed(2));
		return false;
	}
	
	
}


function obterValorCobrado(){
	
	var valor = 0.0;
	
	for(var i = 0; i < cartoes.length; i++){
		valor = cartoes[i].cobrar;
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
	    }
	});
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
	return parseFloat(valor);
}

function floatToText(valor){
	
	var text = (valor < 1 ? "0" : "" ) + Math.floor(valor * 100);
	text = "R$ " + text;
	
	return text.substr(0, text.length - 2) + "," + text.substr(-2);
}

//Executar a função assim que a tela for carregada
window.onload = obterEndereco;