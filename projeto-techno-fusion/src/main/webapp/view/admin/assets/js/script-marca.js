/*################### Adicionado eventos antes de carregar #######################*/
// Ativa as tabs
const triggerTabList = document.querySelectorAll('#nav-tab button');
triggerTabList.forEach(triggerEl => {
    const tabTrigger = new bootstrap.Tab(triggerEl);

    triggerEl.addEventListener('click', event => {
        event.preventDefault();
        tabTrigger.show();
    });
});

// Verifica se houve algum erro, em caso afirmativo muda para a tela de cadastro/atualização 
document.addEventListener('DOMContentLoaded', function () {
    ativarBtnPaginaAtual();

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
    // configurarDadosAbaSalvar();
    limparAbaSalvarAoMudarAba();

    $("#preloader").hide();
});

$(function () {
    // Máscara de dinheiro para o campo salário
    $('#salario').maskMoney({
        prefix: 'R$ ',
        thousands: '.',
        decimal: ','
    });
});

function verificarSeDeveAbrirAbaSalvar() {
    let url = new URL(window.location.href);

    let idMarca = url.searchParams.get("idMarca");

    let urlPura = url.toString().split('?')[0];

    if (idMarca != null && idMarca != '' && urlPura.endsWith('/marca')) {
        var encapuladorIdMarca = $("<button>", {
            id: idMarca
        });

        editarMarca(encapuladorIdMarca);
    }
}


function limparAbaSalvarAoMudarAba() {
    $('#nav-tab button').on('shown.bs.tab', function (e) {
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
        $('#formMarca').submit();
    } else {
        alert('Por favor, preencha todos os campos obrigatórios.');
    }
}

function validarCamposRequired() {
    var camposValidos = true;
    $('#formMarca [required]').each(function () {
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

function editarMarca(e) {
    $("#box-msg").remove();
    $("#preloader").show();

    var formulario = document.getElementById('formMarca');
    var action = formulario.action;
    var id = $(e).attr('id');

    $.ajax({
        method: "get",
        url: action + "/editar",
        data: "id=" + id,
        success: function (response) {
            configDadosEdicaoMarca(response);

            setTabEdicao();
        },
        timeout: 10000
    }).done(function () {
        $("#preloader").hide();

    }).fail(function (xhr, status, errorThrown) {
        $("#preloader").hide();

        if (xhr.status === 403) {
            let url = $('#formMarca').attr('action');
            url = url.replace("marca", "login");

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

function excluirMarca(id) {
    let resultado = confirm("Deseja realmente excluir a marca #" + id + " ?");

    if (resultado) {
        let url = $('#formMarca').attr('action') + "/excluir?id=" + id;

        window.location.href = url;
    }
}

function configDadosEdicaoMarca(json) {
    configTabAtualizarMarca();

    $('#id').val(json.id);
    $('#nome').val(json.nome);

    let btnExcluirNaoExiste = $("#btnExcluirMarca").length < 1;

    if (btnExcluirNaoExiste) {
        $('#btn-acoes-form').append(`<button id="btnExcluirMarca" type="button" class="btn btn-danger me-2"
									onclick="excluirMarca(${json.id})">Excluir</button>`);
    }
}

function configTabAtualizarMarca() {
    $('#titulo-tab').text("Atualizar marca");
    $('#info-tab').text("Informe os novos dados da marca para atualizá-la no sistema");
    $('#nav-profile-tab').text("Atualizar");
}

function setTabEdicao() {
    const triggerEl = document.querySelector('#nav-profile-tab');
    bootstrap.Tab.getInstance(triggerEl).show();
    $('#nome').focus();
}

function restaurarTab() {
    $('#id').val('');
    $('#nome').val('');
    $('#nome').focus();

    $('#titulo-tab').text("Nova marca");
    $('#info-tab').text("Informe os dados da marca para cadastrá-la no sistema");
    $('#nav-profile-tab').text("Cadastrar");
    $('#btnExcluirMarca').remove();

    removerParamUrl();
}

function removerParamUrl() {
    var novaURL = window.location.href;
    var url = new URL(novaURL);

    url.searchParams.delete("idMarca");

    window.history.replaceState({}, document.title, url.href);
}

function ativarBtnPaginaAtual() {
    $('ul-menu > li > a').removeClass('active');
    $('#btnPaginaMarcas').addClass('active');
}
