function teste(){
	
	var id = document.getElementById("id_compra").value;
	var box = document.getElementById("situacao");
	var situacao = box.options[box.selectedIndex].value;
	
	//Requisiçao Ajax para envio da nota situação
	$.ajax({
	    url: '/rest/alterar/situacao/compra/' + situacao + '/' + id,
	    type: 'post',
	    data: {'situacao' : situacao},
	    contentType: 'application/json',
	    success: function(result) {
	    	alert(result);
	    	document.location.reload();
	    }
	});
	
	
}