/*################### Adicionado eventos antes de carregar #######################*/
//Ativa as tabs
const triggerTabList = document.querySelectorAll('#nav-tab button')
triggerTabList.forEach(triggerEl => {
	const tabTrigger = new bootstrap.Tab(triggerEl)

	triggerEl.addEventListener('click', event => {
		event.preventDefault()
		tabTrigger.show()
	})
})

//Verifica se houve algum erro, em caso afirmativo muda para a tela de cadastro/atualização 
document.addEventListener('DOMContentLoaded', function () {
	var operacaoErro = $('#operacaoErro').val();
	var id = $('#id').val();

	if (operacaoErro == 'SAVE' && id != '') {
		configTabAtualizar();
	}

	if (operacaoErro == 'SAVE') {
		setTabEdicao();
	}

	const formatter = new Intl.NumberFormat('pt-BR', {
		currency: 'BRL',
		minimumFractionDigits: 2
	});

	$("#salario").val("R$ " + formatter.format($("#salario").val()));

	let imgString = $('#imagemArmazenada').val();
	if (imgString != '') {
		$('#box-img').attr('src', imgString);
	}

	$("#preloader").hide();
});

//Máscara para o campo de salário
$(function () {
	$('#salario').maskMoney({
		prefix: 'R$ ',
		thousands: '.',
		decimal: ','
	});
})

//Visualizacao da imagem ao selecioná-la no input file
function previewImage() {
	var fileInput = document.getElementById('input-imagem');
	var imagePreview = document.getElementById('box-img');

	if (fileInput.files.length > 0) {
		var file = fileInput.files[0];

		var tamanhoArquivo = file.size; // Tamanho em bytes
		var tamanhoMaximo = 3 * 1024 * 1024; // 3 MB em bytes

		if (tamanhoArquivo > tamanhoMaximo) {
			$("#modalBody").text("O tamanho da imagem deve ser no máximo de 3 MB");
			$("#modalMsg").modal("show");

			fileInput.value = ''; // Limpar o input para evitar o upload do arquivo grande
		} else {
			var reader = new FileReader();

			reader.onload = function (e) {
				imagePreview.src = e.target.result;
			};

			reader.readAsDataURL(file);
		}

	} else {
		imagePreview.src = '/projeto-techno-fusion/view/admin/assets/img/usuario.png';
	}
}

/*################### Eventos e funcões para as botoes #######################*/
function enviarForm() {
	$('#formFuncionario').submit();
}
function enviarFormPesquisa() {
	$('#formPesquisa').submit();
}

function editar(e) {
	$("#box-msg").remove();
	$("#preloader").show();

	var formulario = document.getElementById('formFuncionario');
	var action = formulario.action;
	var id = $(e).attr('id');

	$.ajax({
		method: "get",
		url: action + "/editar",
		data: "id=" + id,
		success: function (response) {
			var json = JSON.parse(response);
			configDadosEdicao(json);
			atualizarTabela();

			setTabEdicao();
		},
		timeout: 5000
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

function excluir(id) {
	let resultado = confirm("Deseja realmente excluir o funcionario #" + id + " ?");

	if (resultado) {
		let url = $('#formFuncionario').attr('action') + "/excluir?id=" + id;

		window.location.href = url;
	}

}

/*
function adicionarTelefone() {
	var novoTelefone = removerFormatacaoTelefone($('#inputTelefone').val());
	var telefonesJSON = $('#telefones');
	var tabela = $('#tabelaTelefones tbody');

	if (novoTelefone !== null && novoTelefone !== '') {
		var telefonesArray = JSON.parse(telefonesJSON.val() || '[]');
		var novoTelefoneJSON = { "numero": novoTelefone };

		var containsTelefone = telefonesArray.some(function (telefoneItem) {
			return JSON.stringify(telefoneItem) === JSON.stringify(novoTelefoneJSON);
		});

		if (!containsTelefone) {
			telefonesArray.push(novoTelefoneJSON);
			var json = JSON.stringify(telefonesArray);

			JSON.stringify(telefonesArray)

			telefonesJSON.val(json);

			atualizarTabela();

			$("#inputTelefone").val("");

		} else {
			alert('Este telefone já existe na lista.');
		}
	} else {
		alert('Por favor, insira um número de telefone válido.');
	}
}*/

function adicionarTelefone() {
	var novoTelefone = removerFormatacaoTelefone($('#inputTelefone').val());
	var telefoneTemp = removerFormatacaoTelefone($('#telefoneTemp').val());
	var telefonesJSON = $('#telefones');
	var tabela = $('#tabelaTelefones tbody');

	if (telefoneTemp !== null && telefoneTemp !== '') {
		var telefonesArray = JSON.parse(telefonesJSON.val() || '[]');

		// Verificar se o telefoneTemp já existe na lista de telefones
		var telefoneExistenteIndex = telefonesArray.findIndex(function (telefoneItem) {
			return telefoneItem.numero === telefoneTemp;
		});

		if (telefoneExistenteIndex !== -1) {
			// Atualizar apenas o atributo "numero" do JSON
			telefonesArray[telefoneExistenteIndex].numero = novoTelefone;
			var json = JSON.stringify(telefonesArray);

			telefonesJSON.val(json);

			atualizarTabela();

			$("#telefoneTemp").val("");
			$("#inputTelefone").val("");

		} else {
			alert('O telefone a ser atualizado não foi encontrado na lista.');
		}
	} else if (novoTelefone !== null && novoTelefone !== '') {
		var telefonesArray = JSON.parse(telefonesJSON.val() || '[]');
		var novoTelefoneJSON = { "numero": novoTelefone };

		var containsTelefone = telefonesArray.some(function (telefoneItem) {
			return JSON.stringify(telefoneItem) === JSON.stringify(novoTelefoneJSON);
		});

		if (!containsTelefone) {
			telefonesArray.push(novoTelefoneJSON);
			var json = JSON.stringify(telefonesArray);

			telefonesJSON.val(json);

			atualizarTabela();

			$("#inputTelefone").val("");

		} else {
			alert('Este telefone já existe na lista.');
		}
	} else {
		alert('Por favor, insira um número de telefone válido.');
	}
}





function editarTelefone(numeroLinha) {
	const tabela = document.getElementById("tabelaTelefones");

	if (numeroLinha <= tabela.tBodies[0].rows.length) {
		const numeroTelefone = removerFormatacaoTelefone(tabela.tBodies[0].rows[numeroLinha - 1].cells[1].textContent);

		$("#inputTelefone").val(numeroTelefone);
		$("#telefoneTemp").val(numeroTelefone);

	} else {
		alert(`O telefone ${numeroTelefone} não foi encontrado.`);
	}
}



function excluirTelefone(numeroLinha) {
	const tabela = document.getElementById("tabelaTelefones");

	const linhas = tabela.tBodies[0].rows;

	let resultado = confirm("Deseja realmente excluir o telefone ?");
	if (resultado) {
		if (linhas.length != 0 && numeroLinha <= linhas.length) {
			numeroTelefone = removerFormatacaoTelefone(linhas[numeroLinha - 1].cells[1].textContent);

			const telefonesArrayInput = $("#telefones");
			const telefonesArray = JSON.parse(telefonesArrayInput.val());

			const indiceTelefone = telefonesArray.findIndex(telefone => telefone.numero === numeroTelefone);
			if (indiceTelefone !== -1) {
				telefonesArray.splice(indiceTelefone, 1);

				telefonesArrayInput.val(JSON.stringify(telefonesArray));

				atualizarTabela();
			} else {
				alert(`O telefone ${numeroTelefone} não foi encontrado.`);
			}
		}
	}
}

function obterNumeroProximaLinha() {
	const tabela = document.getElementById("tabelaTelefones");
	const linhas = tabela.tBodies[0].rows;
	if (linhas.length === 0) {
		return 1;
	} else {
		const valorLinhaAnterior = parseInt(linhas[linhas.length - 1].cells[0].textContent, 10);
		return isNaN(valorLinhaAnterior) ? 1 : valorLinhaAnterior + 1;
	}
}

function atualizarTabela() {
	$('#tabelaTelefones tbody').empty();

	var telefonesArray = JSON.parse($('#telefones').val() || '[]');

	for (var i = 0; i < telefonesArray.length; i++) {
		var telefone = telefonesArray[i];
		var numeroLinha = i + 1;

		var newRow = $('<tr>');
		newRow.append('<td><h6 class="mb-0 text-sm font-weight-semibold">' + numeroLinha + '</h6></td>');
		newRow.append('<td><div class="d-flex px-2 py-1"><div class="d-flex flex-column justify-content-center ms-1"><h6 class="mb-0 text-sm font-weight-semibold">' + formatarNumeroTelefone(telefone.numero) + '</h6></div></div></td>');
		newRow.append('<td class="align-middle"><img onclick="editarTelefone(' + numeroLinha + ')" src="/projeto-techno-fusion/view/admin/assets/img/editar.png" class="px-3" data-bs-toggle="tooltip" data-bs-title="Editar telefone"><img onclick="excluirTelefone(' + numeroLinha + ')" src="/projeto-techno-fusion/view/admin/assets/img/excluir.png" class="px-3" data-bs-toggle="tooltip" data-bs-title="Excluir telefone"></td>');

		$('#tabelaTelefones tbody').append(newRow);
	}
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

function removerFormatacaoTelefone(value) {
	if (!value) return "";

	value = value.replace(/\D/g, '');

	return value;
}

function configDadosEdicao(json) {
	configTabAtualizar();

	$('#id').val(json.id);
	$('#nome').val(json.nome);
	$('#box-img').attr('src', json.imagem);
	$('#email').val(json.email);
	$('#perfil').val(json.perfil);
	$('#login').val(json.login);
	$('#imagemArmazenada').val(json.imagem);
	$('#telefones').val(JSON.stringify(json.telefones));

	var radio = $('input[name="sexo"][value="' + json.sexo + '"]');
	if (radio.length > 0) {
		radio.prop('checked', true);
	}

	let formatter = new Intl.NumberFormat('pt-BR', {
		currency: 'BRL',
		minimumFractionDigits: 2
	});

	$("#salario").val("R$ " + formatter.format(json.salario));

	var date = new Date(json.dataNascimento);
	var day = ("0" + date.getDate()).slice(-2);
	var month = ("0" + (date.getMonth() + 1)).slice(-2);
	var dateString = date.getFullYear() + "-" + (month) + "-" + (day);

	$('#dataNascimento').val(dateString);
}

function configTabAtualizar() {
	$('#titulo-tab').text("Atualizar funcionário");
	$('#info-tab').text("Informe os novos dados do funcionário para atualizá-lo no sistema");
	$('#nav-profile-tab').text("Atualizar");
	$('#groupSenha').css("display", "none");
}

function setTabEdicao() {
	const triggerEl = document.querySelector('#nav-profile-tab');
	bootstrap.Tab.getInstance(triggerEl).show();
	$('#nome').focus();
}

function restaurarTab() {
	$('#formFuncionario :input:not(:button, :submit, :radio, #senha)').val('');
	$("#perfil option:first").prop("selected", true);
	$("input[type='radio'][name='sexo']:first").prop("checked", true);
	$('#nome').focus();

	$('#titulo-tab').text("Novo funcionário");
	$('#info-tab').text("Informe os dados do funcionário para cadastrá-lo no sistema");
	$('#nav-profile-tab').text("Cadastrar");

	$('#box-img').attr('src', '/projeto-techno-fusion/view/admin/assets/img/usuario.png');
	$('#groupSenha').css("display", "block");
	$('#input-imagem').val("");
	limparInputTelefone();

}

function limparInputTelefone() {
	$("#inputTelefone").val("")
}

function showPreloader() {
	document.getElementById('preloader').style.display = 'flex';
}

function hidePreloader() {
	document.getElementById('preloader').style.display = 'none';
}
