$(function(){
	
	var uploadDrop = $("#upload-drop");
	var containerFoto = $('.js-container-foto');
	
	var settings = {
			type: 'json',
			filelimit: 1,
			allow: '*.(jpg|jpeg|png)',
			action: '/fotos/' + uploadDrop.data('codigo'),
			complete: function(foto){
				console.log('>>>>' + foto.url);
				document.location.reload(true);
				document.getElementById("upload-drop").style.display = "none";
				containerFoto.prepend('<img src="' + foto.url + '" class="img-responsive" style="margin: auto"/>');
			}	
	};

	var select = UIkit.uploadSelect($("#upload-select"), settings),
    drop   = UIkit.uploadDrop(uploadDrop, settings);
	
});