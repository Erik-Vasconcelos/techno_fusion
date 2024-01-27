/*################### Adicionado eventos antes de carregar #######################*/
//Ativa as tabs

$(function() { $('ul-menu > li > a').removeClass('active'); $('#btnPaginaFuncionarios').addClass('active'); });

const triggerTabList = document.querySelectorAll('#nav-tab button')
triggerTabList.forEach(triggerEl => {
	const tabTrigger = new bootstrap.Tab(triggerEl)

	triggerEl.addEventListener('click', event => {
		event.preventDefault()
		tabTrigger.show()
	})
})

//Verifica se houve algum erro, em caso afirmativo muda para a tela de cadastro/atualização 
document.addEventListener('DOMContentLoaded', function() {
	$("#preloader").show();

	var operacaoErro = $('#operacaoErro').val();
	var id = $('#id').val();

	if (operacaoErro == 'SAVE' && id != '') {
		configTabAtualizar();
	}

	if (operacaoErro == 'SAVE') {
		setTabEdicao();
	}

	verificarSeDeveAbrirAbaSalvar();
	configurarDadosAbaSalvar();
	limparAbaSalvarAoMudarAba();

	$("#preloader").hide();
});

$(function() {
	$('#salario').maskMoney({
		prefix: 'R$ ',
		thousands: '.',
		decimal: ','
	});
})

function verificarSeDeveAbrirAbaSalvar() {
	let url = new URL(window.location.href);

	let idFuncionario = url.searchParams.get("idFuncionario");

	let urlPura = url.toString().split('?')[0]

	if (idFuncionario != null && idFuncionario != '' && urlPura.endsWith('/funcionario')) {
		var encapuladorIdFuncionario = $("<button>", {
			id: idFuncionario
		});

		editar(encapuladorIdFuncionario);
	}
}

function configurarDadosAbaSalvar() {
	const formatter = new Intl.NumberFormat('pt-BR', {
		currency: 'BRL',
		minimumFractionDigits: 2
	});

	$("#salario").val("R$ " + formatter.format($("#salario").val()));

	let imgString = $('#imagemArmazenada').val();

	if (imgString != '') {

		$('#box-img').attr('src', imgString);
	} else {
		let contexto = window.location.pathname.split('/')[1];
		let imagemUrl = '/' + contexto + '/view/admin/assets/img/usuario.png';

		$('#box-img').attr('src', imagemUrl);
	}
}

function limparAbaSalvarAoMudarAba() {
	$('#nav-tab button').on('shown.bs.tab', function(e) {
		var abaAtiva = e.target.id;

		var id = $('#id').val();

		if (abaAtiva != 'nav-profile-tab' && id != '') {
			restaurarTab();
		}
	});
}

/*################### Eventos e funcões para as botoes #######################*/
function enviarForm() {
	var formularioValido = validarCamposRequired();

	if (formularioValido) {
		$('#formFuncionario').submit();
	} else {
		alert('Por favor, preencha todos os campos obrigatórios.');
	}
}

function validarCamposRequired() {
	var camposValidos = true;
	$('#formFuncionario [required]').each(function() {
		var valorCampo = $(this).val();
		if (!valorCampo.trim()) {
			camposValidos = false;
			return false; // Sair do loop
		}
	});

	return camposValidos;
}

function enviarFormPesquisa() {
	$('#formPesquisa').submit();
}

function getPaginaTelefones() {
	let idFuncionario = $('#id').val();

	let url = $('#formFuncionario').attr('action') + "/telefone?idFuncionario=" + idFuncionario;
	window.location.href = url;
}

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

			reader.onload = function(e) {
				imagePreview.src = e.target.result;
			};

			reader.readAsDataURL(file);
		}

	} else {
		let contexto = window.location.pathname.split('/')[1];
		let imagemUrl = '/' + contexto + '/view/admin/assets/img/usuario.png';

		imagePreview.src = imagemUrl;
	}
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
		success: function(response) {
			configDadosEdicao(response);

			setTabEdicao();
		},
		timeout: 10000
	}).done(function() {
		$("#preloader").hide();

	}).fail(function(xhr, status, errorThrown) {
		$("#preloader").hide();

		if (xhr.status === 401) {
			let url = $('#formFuncionario').attr('action');
			url = url.replace("funcionario", "login");

			window.location.href = url;
		}

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

function configDadosEdicao(json) {
	configTabAtualizar();

	$('#id').val(json.id);
	$('#nome').val(json.nome);

	if (json.imagem != null && json.imagem != '') {
		$('#box-img').attr('src', json.imagem);
		$('#imagemArmazenada').val(json.imagem);

		let btnDownloadNaoExiste = $("#btnDownloadImagem").length < 1;


		if (btnDownloadNaoExiste) {
			let url = $('#formFuncionario').attr('action') + "/downloadImagem?idFuncionario=" + json.id;

			$('#groupPreviewImagem').append(`
				<span class="mt-2 mb-0" id="groupBtnDownloadImagem">
					<a href=${url}>
						<button type="button" id="btnDownloadImagem" class="btn btn-sm btn-dark me-2">Download</button>
					</a>
				</span>`
			);
		}

	} else {
		let contexto = window.location.pathname.split('/')[1];
		let imagemUrl = '/' + contexto + '/view/admin/assets/img/usuario.png';
		$('#box-img').attr('src', imagemUrl);

	}

	$('#email').val(json.email);
	$('#perfil').val(json.perfil);
	$('#login').val(json.login);

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

	let btnExcluirNaoExiste = $("#btnExcluirFuncionario").length < 1;

	if (btnExcluirNaoExiste) {
		$('#btn-acoes-form').append(`<button id="btnExcluirFuncionario" type="button" class="btn btn-danger me-2"
									onclick="excluir(${json.id})">Excluir</button>`);
	}
}

function configTabAtualizar() {
	$('#titulo-tab').text("Atualizar funcionário");
	$('#info-tab').text("Informe os novos dados do funcionário para atualizá-lo no sistema");
	$('#nav-profile-tab').text("Atualizar");
	$('#groupSenha').css("display", "none");
	$('#btnTelefones').attr('disabled', false);
	desativarPopoverTelefone();
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

	let contexto = window.location.pathname.split('/')[1];
	let imagemUrl = '/' + contexto + '/view/admin/assets/img/usuario.png';
	$('#box-img').attr('src', imagemUrl);

	$('#groupSenha').css("display", "block");
	$('#input-imagem').val("");
	$('#btnTelefones').attr('disabled', true);
	$('#groupBtnDownloadImagem').remove();
	$('#btnExcluirFuncionario').remove();

	ativarPopoverTelefone();

	removerParamUrl();
}

function desativarPopoverTelefone() {
	$('#popoverTelefone').popover('dispose');
}

function ativarPopoverTelefone() {
	$('#popoverTelefone').popover();
}

function removerParamUrl() {
	var novaURL = window.location.href;
	var url = new URL(novaURL);

	url.searchParams.delete("idFuncionario");

	window.history.replaceState({}, document.title, url.href);
}