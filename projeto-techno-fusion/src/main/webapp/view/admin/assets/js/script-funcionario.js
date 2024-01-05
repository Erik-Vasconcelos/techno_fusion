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

function configDadosEdicao(json) {
	configTabAtualizar();

	$('#id').val(json.id);
	$('#nome').val(json.nome);
	$('#box-img').attr('src', json.imagem);
	$('#email').val(json.email);
	$('#perfil').val(json.perfil);
	$('#login').val(json.login);
	$('#imagemArmazenada').val(json.imagem);

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
	
}

function showPreloader() {
	document.getElementById('preloader').style.display = 'flex';
}

function hidePreloader() {
	document.getElementById('preloader').style.display = 'none';
}

