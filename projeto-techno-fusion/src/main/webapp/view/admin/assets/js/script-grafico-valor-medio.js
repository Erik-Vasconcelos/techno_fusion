$(function() { $('ul-menu > li > a').removeClass('active'); $('#btnPaginaGraficos').addClass('active'); });
$(function() { $('ul-menu > li > a').removeClass('active'); $('#btnGraficoValorMedio').addClass('active'); });

document.addEventListener('DOMContentLoaded', function() {
	$("#preloader").show();

	$('#btnFiltro').change(function() {
		if ($(this).prop('checked')) {

			$('#groupInput').html(`
				<div class="form-check mb-2">
					<input class="form-check-input" type="radio" name="filtro" id="filtroMaiorQue" checked  value="MAIOR_IGUAL_QUE">
						<label class="custom-control-label" for="filtro">Maior igual a &gt;=</label>
						<br>
						<input class="form-check-input" type="radio" name="filtro" id="filtroMenorQue" value="MENOR_IGUAL_QUE">
						<label class="custom-control-label" for="filtro">Menor igual a &lt;=</label>
						<br>
				</div>
				
	      		<label for="salario" class="form-control-label">Valor</label>
				<input class="form-control" type="text" required="required" id="valor" name="valor">
			`);
			
			$('#valor').maskMoney({
				prefix: 'R$ ',
				thousands: '.',
				decimal: ','
			});
			
		} else {
			$('#groupInput').empty();
		}
	});


	$("#preloader").hide();
});


function gerarGrafico() {

	let data = "";	
	var toggleAtivado = $('#btnFiltro').prop('checked');

    if (toggleAtivado) {
      var valor = $('#valor').val();
      
		if(valor !== undefined && valor.trim() !== ''){
	    	var filtroSelecionado = $('input[name="filtro"]:checked').attr('value');
			
			data = "filtro=" + filtroSelecionado + "&valor=" + valor;
		}else{
			alert("Digite um valor para filtrar o gráfico");
			return;
		}	
    }
    console.log("POassou");

	$("#preloader").show();
	
	let url = $('#url').val();
	$.ajax({
		method: "get",
		url: url,
		data: data,
		success: function(response) {
			console.log(response);
			settarDadosGrafico(response);
			$("#preloader").hide();

		},
		timeout: 10000
	}).done(function() {
		$("#preloader").hide();

	}).fail(function(xhr, status, errorThrown) {
		$("#preloader").hide();

		var boxMessage = document.getElementById("modalBody");

		if (xhr.responseText != null && xhr.responseText != '') {
			boxMessage.textContent = xhr.responseText;
		} else {
			boxMessage.textContent = "Erro na solicitação do recurso ao servidor";
		}

		$("#modalMsg").modal("show");
	});

}

function settarDadosGrafico(json) {
	let ctx = Chart.getChart("myChart");

	if (ctx != undefined) {
		ctx.destroy();
	}

	ctx = document.getElementById('myChart').getContext('2d');

	new Chart(ctx,
		{
			type: 'bar',
			data: {
				labels: json.labels,
				datasets: [{
					label: 'Valor médio do produtos em R$ (Reais)',
					data: json.dataset,
					borderWidth: 1,
					backgroundColor: '#D10024',
				}],
			},

			options: {
				indexAxis: 'y',
				scales: {
					y: {
						beginAtZero: true
					}
				}
			}
		});
}



