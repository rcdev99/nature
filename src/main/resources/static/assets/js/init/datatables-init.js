(function ($) {
    //    "use strict";


    /*  Data Table
    -------------*/




    $('#bootstrap-data-table').DataTable({
        lengthMenu: [[10, 25, 50, 100], [10, 25, 50, 100]],
			
			"language": {
	            "lengthMenu": "Mostrando _MENU_ registros por página",
	            "zeroRecords": "Nenhum registro encontrado",
	            "info": "Página _PAGE_ de _PAGES_ de um total de _TOTAL_ registros",
	            "infoEmpty": "Nenhum registro disponível",
	            "infoFiltered": "(filtrado a partir de _MAX_ registros)",
	            "search": "Pesquisar",
	            "paginate": {
	                "previous": "Anterior",
	                "next": "Próximo"
	              }
	        },        
        
    });



    $('#bootstrap-data-table-export').DataTable({
        dom: 'lBfrtip',
        lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "Todos"]],
        buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print'
        ]
    });
	
	$('#row-select').DataTable({
		
			initComplete: function () {
				this.api().columns().every( function () {
					var column = this;
					var select = $('<select class="form-control"><option value=""></option></select>')
						.appendTo( $(column.footer()).empty() )
						.on( 'change', function () {
							var val = $.fn.dataTable.util.escapeRegex(
								$(this).val()
							);
	 
							column
								.search( val ? '^'+val+'$' : '', true, false )
								.draw();
						} );
	 
					column.data().unique().sort().each( function ( d, j ) {
						select.append( '<option value="'+d+'">'+d+'</option>' )
					} );
				} );
			}
		} );






})(jQuery);