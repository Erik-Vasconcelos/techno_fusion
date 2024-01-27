$(function() { $('ul-menu > li > a').removeClass('active'); $('#btnPaginaRelatorios').addClass('active'); });
$(function() { $('ul-menu > li > a').removeClass('active'); $('#btnRelatorioMarcaProduto').addClass('active'); });

document.addEventListener('DOMContentLoaded', function() {
	$("#preloader").show();

	formatarValores();

	$("#preloader").hide();
});


function verRelatorioMarcaProduto() {
	$('#acao').val("visualizar");
	$('#formRelatorio').submit();
}

function downloadRelatorioMarcaProdutoPdf() {
	$('#acao').val("download-pdf");
	$('#formRelatorio').submit();
}

function downloadRelatorioMarcaProdutoXls() {
	$('#acao').val("download-xls");
	$('#formRelatorio').submit();
}

function formatarValores() {
	var valoresParaFormatar = document.querySelectorAll('.valorParaFormatar');
	valoresParaFormatar.forEach(function(elemento) {
		var valor = parseFloat(elemento.textContent);
		var valorFormatado = valor.toLocaleString('pt-BR', {
			style: 'currency',
			currency: 'BRL'
		});
		elemento.textContent = valorFormatado;
	});

	var descontosParaFormatar = document.querySelectorAll('.descontoParaFormatar');
	descontosParaFormatar.forEach(function(elemento) {
		var desconto = parseFloat(elemento.textContent);
		var descontoFormatado = (desconto / 100).toLocaleString('pt-BR', {
			style: 'percent',
			minimumFractionDigits: 1,
			maximumFractionDigits: 1
		});
		elemento.textContent = descontoFormatado;
	});
}
