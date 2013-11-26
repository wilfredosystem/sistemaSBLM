$(function() {

	$("#idDivGeneralInqui").show(); 
	$("#idDivAdicionalInqui").hide();
	$("#idDivCalificaInqui").hide();

	$(".demo").hide();

	/** **oculta mensaje inquilino general * */

	$( 
			"#frmGeneral\\:inquiApPaterno, #frmGeneral\\:inquiApMaterno, #frmGeneral\\:inquiNombres, #frmGeneral\\:inquiDni, #frmGeneral\\:inquiDireccion")
			.keyup(function() {
				if ($(this).val() != "") {
					$(".error").fadeOut();
					return false;
				}
			});

});

function tabGeneralInqui() {
	$("#idGeneralInqui").attr('style', 'background-color: #ffc600');
	$("#idAdicionalInqui").attr('style', 'background-color: #fed02e');
	$("#idCalificaInqui").attr('style', 'background-color: #fed02e');
	valor = $("#idGeneralInqui").text();// capturamos el valor del div
	$("#lblSubtitulo").text('Datos' + valor);

	$("#idDivGeneralInqui").show();
	$("#idDivAdicionalInqui").hide();
	$("#idDivCalificaInqui").hide();

	// alert($("#pnDerivacion").text());
	// alert($("#pnDerivacion").attr("visible"));
	$("#idDivGeneralInqui").removeAttr("visible");

}
function tabAdicionalInqui() {

	$("#idGeneralInqui").attr('style', 'background-color: #fed02e');
	$("#idAdicionalInqui").attr('style', 'background-color: #ffc600');
	$("#idCalificaInqui").attr('style', 'background-color: #fed02e');
	valor = $("#idAdicionalInqui").text();// capturamos el valor del div
	$("#lblSubtitulo").text('Datos' + valor);

	$("#idDivGeneralInqui").hide();
	$("#idDivAdicionalInqui").show();
	$("#idDivCalificaInqui").hide();

}
function tabCalificaInqui() {
	$("#idGeneralInqui").attr('style', 'background-color: #fed02e');
	$("#idAdicionalInqui").attr('style', 'background-color: #fed02e');
	$("#idCalificaInqui").attr('style', 'background-color: #ffc600');
	valor = $("#idCalificaInqui").text();// capturamos el valor del div
	$("#lblSubtitulo").text('Datos' + valor);

	$("#idDivGeneralInqui").hide();
	$("#idDivAdicionalInqui").hide();
	$("#idDivCalificaInqui").show();
}

/** *********validacion inquilino general************* */

function validacioGeneralInqui() {
	var letras = /^[a-zA-Z]+$/;
	$(".error").remove();
	if ($("#frmGeneral\\:inquiTipoPersona option:selected").val() == 0) {
		$("#frmGeneral\\:inquiTipoPersona").focus().after(
				"<span class='error'>Seleccione tipo persona</span>");
		return false;
	} else if ($("#frmGeneral\\:inquiTipoEntidad option:selected").val() == 0) {
		$("#frmGeneral\\:inquiTipoEntidad").focus().after(
				"<span class='error'>Seleccione tipo Entidad</span>");
		return false;
	} else if ($("#frmGeneral\\:inquiApPaterno").val() == "") {
		$("#frmGeneral\\:inquiApPaterno").focus().after(
				"<span class='error'>Ingrese Apellido Paterno</span>");
		return false;
	} else if (!letras.test($("#frmGeneral\\:inquiApPaterno").val())) {
		$("#frmGeneral\\:inquiApPaterno")
				.focus()
				.after(
						"<span class='error'>Incorrecto!!! no debe contener numeros...</span>");
		return false;
	} else if ($("#frmGeneral\\:inquiApMaterno").val() == "") {
		$("#frmGeneral\\:inquiApMaterno").focus().after(
				"<span class='error'>Ingrese Apellido Materno</span>");
		return false;
	} else if (!letras.test($("#frmGeneral\\:inquiApMaterno").val())) {
		$("#frmGeneral\\:inquiApMaterno")
				.focus()
				.after(
						"<span class='error'>Incorrecto!!! no debe contener numeros...</span>");
		return false;
	} else if ($("#frmGeneral\\:inquiNombres").val() == "") {
		$("#frmGeneral\\:inquiNombres").focus().after(
				"<span class='error'>Ingrese nombres</span>");
		return false;
	} else if (!letras.test($("#frmGeneral\\:inquiNombres").val())) {
		$("#frmGeneral\\:inquiNombres")
				.focus()
				.after(
						"<span class='error'>Incorrecto!!! no debe contener numeros...</span>");
		return false;
	} else if ($("#frmGeneral\\:inquiDni").val() == "") {
		$("#frmGeneral\\:inquiDni").focus().after(
				"<span class='error'>Ingrese DNI</span>");
		return false;
	} else if (isNaN($("#frmGeneral\\:inquiDni").val())) {
		$("#frmGeneral\\:inquiDni").focus().after(
				"<span class='error'>DNI debe ser numerico</span>");
		return false;
	} else if ($("#frmGeneral\\:inquiDni").val().length < 8) {
		$("#frmGeneral\\:inquiDni").focus().after(
				"<span class='error'>El DNI debe tener 8 digitos</span>");
		return false;

	} else if ($("#frmGeneral\\:inquiDireccion").val() == "") {
		$("#frmGeneral\\:inquiDireccion").focus().after(
				"<span class='error'>Ingrese Direccion</span>");
		return false;
	} else if ($("#frmGeneral\\:cbxLstDptos option:selected").val() == 0) {
		$("#frmGeneral\\:cbxLstDptos").focus().after(
				"<span class='error'>Seleccione Departamento</span>");
		return false;
	} else if ($("#frmGeneral\\:cbxLstProvincias option:selected").val() == 0) {
		$("#frmGeneral\\:cbxLstProvincias").focus().after(
				"<span class='error'>Seleccione Provincia</span>");
		return false;
	} else if ($("#frmGeneral\\:cbxLstDistritos option:selected").val() == 0) {
		$("#frmGeneral\\:cbxLstDistritos").focus().after(
				"<span class='error'>Seleccione Distrito</span>");
		return false;
	} else {
		dlgRegistrarInquilinoGeneral.show();
	}

};
function validacionAdicionalInqui() {
	var emailreg = /^[a-zA-Z0-9_\.\-]+@[a-zA-Z0-9\-]+\.[a-zA-Z0-9\-\.]+$/;

	$(".error").remove();
	if ($("#frmAdicional\\:inquiApPaterno").val() == "") {
		msjRegistralx.renderMessage({
			summary : 'Notificacion',
			detail : 'Debe seleccionar un registro de la tabla',
			severity : 0
		});
	} else if (isNaN($("#frmAdicional\\:inquiTelefono").val())) {
		$("#frmAdicional\\:inquiTelefono").focus().after(
				"<span class='error'>Telefono debe ser numerico</span>");
		return false;
	} else if ($("#frmAdicional\\:inquiTelefono").val().length < 7) {
		$("#frmAdicional\\:inquiTelefono").focus().after(
				"<span class='error'>Ingrese numero de telefono Valido</span>");
		return false;
	} else if (isNaN($("#frmAdicional\\:inquiCelular").val())) {
		$("#frmAdicional\\:inquiCelular").focus().after(
				"<span class='error'>Celular debe ser numerico</span>");
		return false;
	} else if ($("#frmAdicional\\:inquiCelular").val().length < 9) {
		$("#frmAdicional\\:inquiCelular").focus().after(
				"<span class='error'>Numero de digitos inValido</span>");
		return false;
	} else if ($("#frmAdicional\\:inquiEmail").val()!='' && !emailreg.test($("#frmAdicional\\:inquiEmail").val())) {
		$("#frmAdicional\\:inquiEmail").focus().after(
				"<span class='error'>Email Incorrecto</span>");
		return false;
	} else {

		dlgRegistrarInquilinoAdicional.show();
	}
};

function validacionCalificaInqui() {

	$(".error").remove();
	if ($("#frmCalifica\\:inquiApPaterno").val() == "") {

		msjTecnicox.renderMessage({
			summary : 'Notificacion',
			detail : 'Debe seleccionar un registro de la tabla',
			severity : 0
		});

		return false;

	} else if ($("#frmTecnico\\:txtDenominacion").val() == "") {
		$("#frmTecnico\\:txtRegSbn").focus().after(
				"<span class='error'>Ingrese Denominacion</span>");
		return false;
	} else if ($("#frmTecnico\\:txtTitularidad option:selected").val() == 0) {
		$("#frmTecnico\\:txtTitularidad").focus().after(
				"<span class='error'>Seleccione titularidad</span>");
		return false;
	} else if ($("#frmTecnico\\:txtTipoTitularidad option:selected").val() == 0) {
		$("#frmTecnico\\:txtTipoTitularidad").focus().after(
				"<span class='error'>Seleccione tipo titularidad</span>");
		return false;
		
	} else {

		dlgRegistrarInquilinoCalifica.show();
	}
};
function limpiarvalidacion() {
	if ($("#frmGeneral\\:inquiTipoPersona option:selected").val() != "0") {

		$(".error").fadeOut();
		return false;
	}
}