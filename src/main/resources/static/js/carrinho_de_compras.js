/**
 * Variáveis globias
 */

	//Variável para definir o valor mínimo de compras para frete gratuito
var margemFrete = textToFloat("R$ 50,00");
var cupons = [];
var produtosCarrinho = [];

/**
 * Funcionalidades para cálculo do total da venda  valores dos produtos
 */
//Função para cálculo do valor total dos produtos no carrinho
function calcularTotalProduto(){
	
	var produtos = document.getElementsByClassName("produto");
	var totalProdutos = 0.0;
	
		for(pos = 0; pos < produtos.length; pos++){
			
			//Obtendo valor do produto
			var precoDoProduto = produtos[pos].getElementsByClassName("valorProduto"); 
			var precoTxt = precoDoProduto[0].innerHTML;
			var preco = textToFloat(precoTxt);
			//Obtendo quantidade de produto
			var quantidadeDeProduto = produtos[pos].getElementsByClassName("quantity form-control input-number");
			var quantidadeTxt = quantidadeDeProduto[0].value;
			var quantidade = textToFloat(quantidadeTxt);
			//calculando total de um produto
			var subTotal = (preco * quantidade); 
			//Exibindo valor total de um produto
			var totalProduto = produtos[pos].getElementsByClassName("totalProduto");
			totalProduto[0].innerHTML = floatToText(subTotal);
			//Incrementando total com o valor final de cada produto
			totalProdutos += subTotal;
		}
		
		totalVenda(totalProdutos);
		return totalProdutos;
		
}

//Escrever total de um produto
function totalVenda(valor){
	
	var total = document.getElementsByClassName("totalProdutos");
	
	if(valor > 0){
		
		total[0].innerHTML = floatToText(valor);
	}
	
	totalCompra();
}

//Escrever total da compra
function totalCompra(){
	
	//Obter os valores de composição do valor total da compra
	var totalProdutoTxt = document.getElementsByClassName("totalProdutos");
	var totalProduto = textToFloat(totalProdutoTxt[0].innerHTML);
	var totalFreteTxt = document.getElementsByClassName("frete");
	var totalFrete = textToFloat(totalFreteTxt[0].innerHTML);
	var totalDescontoTxt = document.getElementsByClassName("desconto");
	var totalDesconto = textToFloat(totalDescontoTxt[0].innerHTML);
	var totalCompraTxt = document.getElementsByClassName("totalVenda");
	//Total da compra
	var totalCompra = 0.0;
	
	//Validações
	if(totalProduto > margemFrete){
		totalFrete = 0.0;
		totalFreteTxt[0].innerHTML = "R$ 0.00";
	}
	//calculando valor total da compra
	totalCompra = ((totalProduto + totalFrete) - totalDesconto);
	
	//Cupom de desconto cobre o valor da venda?
	if(totalCompra < 0){
		//Converter o resíduo da venda em cupom de desconto
		var residuo = totalCompra * -1;
		residuo = floatToText(residuo);
		alert("A compra vai gerar um cupom de desconto no valor de: " + residuo);
		//Inserir valor zerado neste caso
		totalCompraTxt[0].innerHTML = "R$ 0.00";
	}else{
		//Inserir valor total da compra
		totalCompraTxt[0].innerHTML = floatToText(totalCompra);
	}
	//valor ainda está zerado ?
	if(totalCompra == 0){
		totalCompraTxt[0].innerHTML = "R$ 0.00";
	}
}

/**
 * Funcionalidades ligadas ao frete da venda
 */
//Função responsável por realizar o cálculo do Frete
function calcularFrete(){
	
	var totalProdutosTxt = document.getElementsByClassName("totalProdutos");
	var frete = document.getElementsByClassName("frete");
	var total = textToFloat(totalProdutosTxt[0].innerHTML); 
	//Variável para definir o valor mínimo de compras para frete gratuivo
	//var margemFrete = textToFloat("R$ 50,00");
	//Validações
	if(validarCep()){
		if(total < margemFrete){
			frete[0].innerHTML = floatToText(11.87);
		}else{
			alert("Frete grátis para compras acima de R$ " + margemFrete);
			frete[0].innerHTML = floatToText(0.0);
		}
	}
	//Calcular o valor total da venda após inserção do frete
	totalCompra();
}

//função para validar o CEP
function validarCep(){
	//Tratamento de dados
	var cep = document.getElementById("cep");
	cep = cep.value.replace(/[^\d]+/g,'');
	//Validação do CEP
	if(cep.length != 8){
		alert("CEP Inválido !");
		return false;
	}
	
	return true;
}

/**
 * Funcionalidades ligadas ao cupom de desconto
 */
//Função que realiza requisição via REST e retorna um cupom caso ele exista
function validarCupom(){
	//Obtém o código do cupom
	var codigo = document.getElementById("cupom_codigo");
	//Requisiçao Ajax para validação do cupom
	$.ajax({
	    url: '/rest/validar/cupom/' + codigo.value,
	    type: 'post',
	    data: JSON.stringify(codigo),
	    contentType: 'application/json',
	    success: function(result) {
	    	
	    	var cupom = JSON.parse(result);
	    	
	        if(cupom != null){
	    	  verificarCupom(cupom);
	        }
	      
	    }
	  });
	
}

//Função para verificar se o cupom esta ativo
function verificarCupom(cupom){
	//Verifica se o cupom está ativo
	if(cupom.ativo){
		//Verifica se o cupom já foi inserido anteriormente
		if(validaInsercao(cupom)){
			var valorDesc = calcularDesconto();
			escreverValorDesconto(valorDesc);
		}
	}else{
		alert("O cupom " + cupom.codigo + " esta inátivo");
	}
}

//Escrever valor do cupom na view
function escreverValorDesconto(valor){
	//Obtém a referência do local de exibição do valor de desconto
	var desconto = document.getElementsByClassName("desconto");
	//Insere o texto correspondente ao valor de desconto
	if(valor != null){
		desconto[0].innerHTML = floatToText(valor);
	}
	//Invoca método para calcular o total da compra
	totalCompra();
}

//valida a inserção do cupom
function validaInsercao(cupom){
	
	var valido = true;
	//percorre array de cupons
	for(var i=0; i < cupons.length; i++){
		
		if(cupons[i].id == cupom.id){
			valido = false;
		}
	}
	//Cupom pode ser inserido ?
	if(valido){
		cupons.push(cupom);
	}else{
		alert("O cupom " + cupom.codigo + " já foi inserido !");
	}
	
	return valido;

}

//Obtém o valor total dos descontos com base nos cupons inseridos
function calcularDesconto(){
	
	var totalDesc = 0.0;
	
	for(var i = 0; i < cupons.length ; i++){
		
		totalDesc += cupons[i].valor;
	}
	
	return totalDesc;
}

/**
 * Funções para validação da compra
 */

function validandoCompra(logado){
	
	if(!logado){
		alert("Para prosseguir com a compra, faça login ou cadastre-se");
		window.location = "/login";
		return;
	}
	
	var produtos = document.getElementsByClassName("produto");
	produtosCarrinho = [];
	
	for(pos = 0; pos < produtos.length; pos++){
		
		//Obtendo id do produto
		var idDoProduto = produtos[pos].getElementsByClassName("produtoId");
		var id = idDoProduto[0].value;
		//Obtendo quantidade de produto
		var quantidadeDeProduto = produtos[pos].getElementsByClassName("quantity form-control input-number");
		var quantidadeTxt = quantidadeDeProduto[0].value;
		var quantidade = textToFloat(quantidadeTxt);
		
		var itemCompra = new Object();
		
		itemCompra.id = id;
		itemCompra.quantidade = quantidade;
		
		produtosCarrinho.push(itemCompra);
	}
	
	prepararCompra();
}

function prepararCompra(){
	
	if(produtosCarrinho == 0){
		alert("O carrinho está vázio, insira ao menos um produto para prosseguir com a compra");
	}else{
		var produtos = JSON.stringify(produtosCarrinho);
		var cuponsDesc = JSON.stringify(cupons);
		
		//Requisiçao Ajax para envio dos dados da compra
		const register = $.ajax({
		    url: '/rest/checkout',
		    type: 'post',
		    data: {'produtos': produtos,
		    	   'cupons': cuponsDesc
		    	},
		    success: function(result) {
		    	var retorno = JSON.parse(result);
		    	if(retorno.situacao){
		    		window.location = "/compra/conclusao";
		    	}else{
		    		alert(retorno.mensagem);
		    	}
		    }
		  });
	}
}

/**
 * Funcionalidades de cunho geral
 */
//Converter texto para float
function textToFloat(text){
	
	var valor = text.replace("R$ ", "").replace(",",".");
	return parseFloat(valor);
}

//Converter float para texto
function floatToText(valor){
	
	var text = (valor < 1 ? "0" : "" ) + Math.floor(valor * 100);
	text = "R$ " + text;
	
	return text.substr(0, text.length - 2) + "," + text.substr(-2);
}

/**
 * Funcionalidades relacionadas com a inicialização da página
 */
//Função a ser executada no momento em que a página for carregada
function onDocumentLoad(){
	//Função responsável pelo cálculo do valor dos produtos
	calcularTotalProduto();
	totalCompra();
}

/**
 * Funcionalidades ligadas a execução da página
 */
//Executar a função assim que a tela for carregada
window.onload = onDocumentLoad;
//Executar a função antes da tela ser fechada ou alterada
window.addEventListener("beforeunload", function (event) {
	  event = console.log("eureca!");	
});