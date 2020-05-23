//Variáveis locais
	//Variável para definir o valor mínimo de compras para frete gratuito
	var margemFrete = textToFloat("R$ 50,00");

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
		alert("A compra vai gerar um cupom de desconto no valor de: " + residuo);
		//Inserir valor zerado neste caso
		totalCompraTxt[0].innerHTML = floatToText(0.0);
		
	}else{
		
		//Inserir valor total da compra
		totalCompraTxt[0].innerHTML = floatToText(totalCompra);
	}
	
	if(totalCompra == 0){
		totalCompraTxt[0].innerHTML = "R$ 0.00";
	}
}


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

//Função a ser executada no momento em que a página for carregada
function onDocumentLoad(){
	//Função responsável pelo cálculo do valor dos produtos
	calcularTotalProduto();
	totalCompra();
}

//Para executar a função assim que a tela for carregada
window.onload = onDocumentLoad;