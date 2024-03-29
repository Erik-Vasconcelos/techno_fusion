
$(function () { $('ul-menu > li > a').removeClass('active'); $('#btnPaginaInicio').addClass('active'); });

document.addEventListener('DOMContentLoaded', function () {
	$("#preloader").show();
	$("#preloader").hide();
	
	$(function() {
	$.getJSON("https://economia.awesomeapi.com.br/json/last/USD-BRL,EUR-BRL,AUD-BRL,BTC-BRL", function(dados) {
		settarValores(dados);
	});
})
});



function settarValores(data) {
	 const formatter = new Intl.NumberFormat('pt-BR', {
        currency: 'BRL',
        minimumFractionDigits: 2
    });
	
	const valorDolar = (parseFloat(data['USDBRL'].high) + parseFloat(data['USDBRL'].low)) / 2;
	$('#valorUSDBRL').text(`R$ ${formatter.format(valorDolar.toFixed(2))}`);
	settarVariacao('#variacaoUSDBRL', data['USDBRL'].pctChange);

	const valorEuro = (parseFloat(data['EURBRL'].high) + parseFloat(data['EURBRL'].low)) / 2;
	$('#valorEURBRL').text(`R$ ${formatter.format(valorEuro.toFixed(2))}`);
	settarVariacao('#variacaoEURBRL', data['EURBRL'].pctChange);

	const valorDolarAustraliano = (parseFloat(data['AUDBRL'].high) + parseFloat(data['AUDBRL'].low)) / 2;
	$('#valorAUDBRL').text(`R$ ${formatter.format(valorDolarAustraliano.toFixed(2))}`);
	settarVariacao('#variacaoAUDBRL', data['AUDBRL'].pctChange);

	const valorBitcoin = (parseFloat(data['BTCBRL'].high) + parseFloat(data['BTCBRL'].low)) / 2;
	$('#valorBTCBRL').text(`R$ ${formatter.format(valorBitcoin.toFixed(2))}`);
	settarVariacao('#variacaoBTCBRL', data['BTCBRL'].pctChange);

}

function settarVariacao(idBox, variacao) {
	if (variacao > 0) {
		$(idBox)
			.html(`<span class="text-sm font-weight-bolder">
                       Variação <i class="fa fa-chevron-up text-xs me-1"> ${variacao}%
                    </span>`)
			.addClass('text-success');
	} else if(variacao == 0){
		$(idBox)
			.html(`<span class="text-sm font-weight-bolder">
                       Variação ${variacao}%
                    </span>`);
	} else {
		$(idBox)
			.html(`<span class="text-sm font-weight-bolder">
                       Variação <i class="fa fa-chevron-down text-xs me-1"> ${variacao}%
                      </span>`)
			.addClass('text-danger');
	}
}
