$(document)
		.ready(
				function() {

					/** **oculta mensaje super usuarios* */
					$(
					"#formPanelRegistroArbitrio\\:idmonto")
							.keyup(function() {
								if ($(this).val() != "") {
									$(".error").fadeOut();
									return false;
								}
							});
					
//					$(
//					"#formPanelRegistroCarta\\:idclave, #formPanelRegistroCarta\\:idjravcalle, #formPanelRegistroCarta\\:idnro , #formPanelRegistroCarta\\:idintdtostand , #formPanelRegistroCarta\\:idCajaInquilino ")
//					.keyup(function() {
//						if ($(this).val() != "") {
//							$(".error").fadeOut();
//							return false;
//						}
//					});



				});




function validacionRecaudacionArbitrio() {
	

	$(".error").remove();

	var hasError = false;
	
	
	if ($("#formPanelRegistroArbitrio\\:idmonto").val() == "") {
		$("#formPanelRegistroArbitrio\\:idmonto").focus().after(
				"<span class='error'>Ingrese monto</span>");
		return false;
	} else 
	{

		widgetDlgConfirmGuardarArbitrio.show();

	}

}

function validacionRecaudacionCarta() {

	$(".error").remove();

	var hasError = false;
	


		widgetDlgConfirmGuardarCarta.show();

	

}




