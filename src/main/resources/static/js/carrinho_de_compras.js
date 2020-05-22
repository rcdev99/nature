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
	
	var total = document.getElementsByClassName("totalVenda");
	
	if(valor > 0){
		
		total[0].innerHTML = floatToText(valor);
	}
}


function onDocumentLoad(){
	
	calcularTotalProduto()
	
}

//Para executar a função assim que a tela for carregada
window.onload = onDocumentLoad;