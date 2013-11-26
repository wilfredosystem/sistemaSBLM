$(document)
		.ready(
				function() {

					/** **oculta mensaje super usuarios* */
					$(
							"#frmPropiedades\\:idContratoNumero, #frmPropiedades\\:idContratoAnho, #frmPropiedades\\:idhiddenUpa, #frmPropiedades\\:idhiddenInquilino, #frmPropiedades\\:idInicioContrato, #frmPropiedades\\:idFinContrato ")
							.keyup(function() {
								if ($(this).val() != "") {
									$(".error").fadeOut();
									return false;
								}
							});
					
//					$(
//					"#frmRepresentante\\:idnombres, #frmRepresentante\\:idapellidos, #frmRepresentante\\:idruc , #frmRepresentante\\:iddni ")
//					.keyup(function() {
//						if ($(this).val() != "") {
//							$(".error").fadeOut();
//							return false;
//						}
//					});



				});



//
//function validacionRepresentante() {
//
//	var hasError = false;
//	
//	if ($("#frmRepresentante\\:idnombres").val() == "") {
//		$("#frmRepresentante\\:idnombres").focus().after(
//				"<span class='error'>Ingrese la nombres </span>");
//		return false;
//	} else if ($("#frmRepresentante\\:idapellidos").val() == "") {
//		$("#frmRepresentante\\:idapellidos").focus().after(
//				"<span class='error'>Ingrese apellidos</span>");
//		return false;
//	} else if ($("#frmRepresentante\\:idruc").val() == "") {
//		$("#frmRepresentante\\:idruc").focus().after(
//				"<span class='error'>Ingrese ruc </span>");
//		return false;
//	} else  if ($("#frmRepresentante\\:iddni").val() == "") {
//		$("#frmRepresentante\\:iddni").focus().after(
//		"<span class='error'>Ingrese dni </span>");
//		return false;
//	}
//	{
//
//		dlgRegistrarRepresentante.show();
//
//	}
//
//}

function validacionContratoPropiedades() {
	$(".error").remove();

	var hasError = false;
	
	if ($("#frmPropiedades\\:idContratoTipo option:selected").val() == "") {
		$("#frmPropiedades\\:idContratoTipo").focus().after(
				"<span class='error'>Seleccione tipo de contrato</span>");
		return false;
	} else if ($("#frmPropiedades\\:idContratoNumero").val() == "") {
		$("#frmPropiedades\\:idContratoNumero").focus().after(
				"<span class='error'>Ingrese n&uacutemero de contrato</span>");
		return false;
	} else if ($("#frmPropiedades\\:idContratoAnho option:selected").val() == "0") {
		$("#frmPropiedades\\:idContratoAnho").focus().after(
				"<span class='error'>Ingrese año de contrato </span>");
		return false;
	}else if ($("#frmPropiedades\\:idhiddenUpa").val() == "") {
		$("#frmPropiedades\\:idhiddenUpa").focus().after(
		"<span class='error'>Seleccione Upa </span>");
	return false;
	}else if ($("#frmPropiedades\\:idhiddenInquilino").val() == "") {
		$("#frmPropiedades\\:idhiddenInquilino").focus().after(
		"<span class='error'>Seleccione inquilino </span>");
	return false;
	}
	else if ($("#frmPropiedades\\:idInicioContrato").val() == "") {
		$("#frmPropiedades\\:idInicioContrato").focus().after(
		"<span class='error'>Seleccione fecha de inicio del contrato </span>");
	return false;
	}
	else if ($("#frmPropiedades\\:idFinContrato").val() == "") {
		$("#frmPropiedades\\:idFinContrato").focus().after(
		"<span class='error'>Seleccione fecha de fin del contrato </span>");
	return false;
	}else 
	{
		dlgRegistrarContrato.show();
	}

}

function limpiarvalidacion() {
	if ($("#frmPropiedades\\:idContratoTipo option:selected").val() != "") {

		$(".error").fadeOut();
		return false;
	}
}

function limpiarvalidacionAnioContrato() {
	if ($("#frmPropiedades\\:idContratoAnho option:selected").val() != "") {

		$(".error").fadeOut();
		return false;
	}
}





