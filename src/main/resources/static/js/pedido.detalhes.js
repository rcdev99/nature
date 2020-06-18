var itensSelecionados = [];
var id_compra = document.getElementById("id_compra").value;

function concluir(){
	
	obterItensSelecionados();
		
	if(itensSelecionados.length > 0){
		
		var itens = JSON.stringify(itensSelecionados);
		var compra_id = JSON.stringify(id_compra);
		
		$.ajax({
		    url: '/rest/solicitar/troca',
		    type: 'post',
		    data: {'itens': itens,
		    	   'id_compra': compra_id
		    },
		    success: function(result) {
		    	
		      alert(result);
		      window.location = "/pedidos/meus";
		    }
		});
	}else{
		alert("Para proceder com a troca selecione ao menos um item");
	}
}

function obterItensSelecionados(){
	
	var checkboxes = document.querySelectorAll('input[type=checkbox]:checked');
	
	for (var i = 0; i < checkboxes.length; i++ ){
		
		var itemTroca = new Object(); 
		coluna_qtd = document.getElementById(checkboxes[i].value).getElementsByTagName("td").namedItem("produto_quantidade").getElementsByTagName("input");
		qtd = coluna_qtd.namedItem(checkboxes[i].value+"quant").value;
		
		itemTroca.id = checkboxes[i].value;
		itemTroca.quantidade = qtd;
		
		itensSelecionados.push(itemTroca);
	}
}

function process(quant,id){
	
	var value = parseInt(document.getElementById(id+"quant").value);
    var max = (document.getElementById(id+"quant").max);
    value+=quant;
    
    if(value < 1){
      document.getElementById(id+"quant").value = 1;
    }
    if(value > max){
    	document.getElementById(id+"quant").value = max;
    }
    if(!(value < 1) && !(value > max) ){
    document.getElementById(id+"quant").value = value;
    }
}