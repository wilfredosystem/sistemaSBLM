$(function() {
			var argumentos;

			$("#idDivSeguimiento").show();
			$("#idDivConsulta").hide();
			$("#idDivAlerta").hide();
			$("#idDivReporte").hide();

			$(".demo").hide();
			

			// CIERRA BLOQUE QUE OCULTA
		});
		///cmbiado de tabs

		function tabSeguimiento() {
			$("#idSeguimiento").attr('style', 'background-color: #ffc600');
			$("#idConsulta").attr('style', 'background-color: #fed02e');
			$("#idAlerta").attr('style', 'background-color: #fed02e');
			$("#idReporte").attr('style', 'background-color: #fed02e');
			valor = $("#idSeguimiento").text();//capturamos el valor del div
			$("#lblSubtitulo").text(valor);

			$("#idDivSeguimiento").show();
			$("#idDivConsulta").hide();
			$("#idDivAlerta").hide();
			$("#idDivReporte").hide();

			
			//$("#idDivSeguimiento").removeAttr("visible");

		}
function tabConsulta() {
			
			$("#idSeguimiento").attr('style', 'background-color: #fed02e');
			$("#idConsulta").attr('style', 'background-color: #ffc600');
			$("#idAlerta").attr('style', 'background-color: #fed02e');
			$("#idReporte").attr('style', 'background-color: #fed02e');
			valor = $("#idConsulta").text();//capturamos el valor del div
			$("#lblSubtitulo").text(valor);

			$("#idDivSeguimiento").hide();
			$("#idDivConsulta").show();
			$("#idDivAlerta").hide();
			$("#idDivReporte").hide();

		}
		function tabAlerta() {
			
			$("#idSeguimiento").attr('style', 'background-color: #fed02e');
			$("#idConsulta").attr('style', 'background-color: #fed02e');
			$("#idAlerta").attr('style', 'background-color: #ffc600');
			$("#idReporte").attr('style', 'background-color: #fed02e');
			valor = $("#idAlerta").text();//capturamos el valor del div
			$("#lblSubtitulo").text(valor);

			$("#idDivSeguimiento").hide();
			$("#idDivConsulta").hide();
			$("#idDivAlerta").show();
			$("#idDivReporte").hide();

		}
		function tabReporte() {
			$("#idSeguimiento").attr('style', 'background-color: #fed02e');
			$("#idConsulta").attr('style', 'background-color: #fed02e');
			$("#idAlerta").attr('style', 'background-color: #fed02e');
			$("#idReporte").attr('style', 'background-color: #ffc600');
			valor = $("#idReporte").text();//capturamos el valor del div
			$("#lblSubtitulo").text(valor);

			$("#idDivSeguimiento").hide();
			$("#idDivConsulta").hide();
			$("#idDivAlerta").hide();
			$("#idDivReporte").show();
		}