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

function escreverqtd(qtd){
	var elemento = document.getElementById("carrinho");
	elemento.innerHTML = " [" + qtd + "]";
}