/*################### Adicionado eventos antes de carregar #######################*/

document.addEventListener('DOMContentLoaded', function () {
	$("#preloader").show();
	formatarNumerosTelefoneNaTabela();
	$("#preloader").hide();
});

/*################### Eventos e funcões para as botoes #######################*/
function enviarForm() {
	let campoNumero = $('#numero');

	campoNumero.val(removerFormatacaoTelefone(campoNumero.val()));
	$('#formTelefone').submit();
}

function limparForm() {
	$('#idTelefone').val("");
	$('#numero').val("");
}

function editar(id) {
	$("#box-msg").remove();
	$("#preloader").show();

	var formulario = document.getElementById('formTelefone');
	var action = formulario.action;

	$.ajax({
		method: "get",
		url: action + "/editar",
		data: "id=" + id,
		success: function (response) {
			configDadosEdicaoJson(response);

			$('#numero').focus();
		},
		timeout: 10000
	}).done(function () {
		$("#preloader").hide();

	}).fail(function (xhr, status, errorThrown) {
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

function excluirTelefone(id) {
	let resultado = confirm("Deseja realmente excluir o telefone #" + id + " ?");

	if (resultado) {
		let url = $('#formTelefone').attr('action') + "/excluir?id=" + id;

		window.location.href = url;
	}
}

function configDadosEdicaoJson(json) {
	$('#idTelefone').val(json.id);
	$('#numero').val(formatarNumeroTelefone(json.numero));
}

const formatarTelefone = (event) => {
	let input = event.target;
	input.value = formatarNumeroTelefone(input.value);
}

const formatarNumeroTelefone = (value) => {
	if (!value) return "";

	value = value.replace(/\D/g, '');

	value = value.slice(0, 11);
	value = value.replace(/(\d{2})(\d)/, "($1) $2")
	value = value.replace(/(\d)(\d{4})$/, "$1-$2")

	return value;
}

function formatarNumerosTelefoneNaTabela() {
	var celulasTelefone = $("#tabelaTelefones td:nth-child(2)");

	celulasTelefone.each(function () {
		var numeroTelefone = $(this).text().trim();

		var numeroFormatado = formatarNumeroTelefone(numeroTelefone);

		var novoHtml = `
            <div class="d-flex px-2 py-1">
              <div class="d-flex flex-column justify-content-center ms-1">
                <h6 class="mb-0 text-sm font-weight-semibold">${numeroFormatado}</h6>
              </div>
            </div>`;

		$(this).html(novoHtml);

	});
}

function removerFormatacaoTelefone(value) {
	if (!value) return "";

	value = value.replace(/\D/g, '');

	return value;
}
