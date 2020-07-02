function addcarrinho(id){
	//Requisição ajax para inserção de item ao carrinho de compras
	$.ajax({
	    url: '/rest/add/item/' + id,
	    type: 'post',
	    data: JSON.stringify(id),
	    contentType: 'application/json',
	    success: function(result) {
	    	
	    	var qtd = JSON.parse(result);
	    	escreverqtd(qtd);
	    }
	});
}

function addProdutoQuantidade(){
	
	var id = document.getElementById("id_produto").value;
	var qtd = document.getElementById("quantity").value;
	
	//Requisição ajax para inserção de item ao carrinho de compras
	$.ajax({
	    url: '/rest/add/item/' + id + '/' + qtd,
	    type: 'post',
	    data: JSON.stringify(id),
	    contentType: 'application/json',
	    success: function(result) {
	    	window.location = "/carrinho";
	    }
	});
}

function escreverqtd(qtd){
	var elemento = document.getElementById("carrinho");
	elemento.innerHTML = " [" + qtd + "]";
}