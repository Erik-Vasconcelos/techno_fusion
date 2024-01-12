/*################### Adicionado eventos antes de carregar #######################*/
// Ativa as tabs
const triggerTabList = document.querySelectorAll('#nav-tab button')
triggerTabList.forEach(triggerEl => {
    const tabTrigger = new bootstrap.Tab(triggerEl)

    triggerEl.addEventListener('click', event => {
        event.preventDefault()
        tabTrigger.show()
    })
})

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
    configurarDadosAbaSalvar();
    limparAbaSalvarAoMudarAba();

    $("#preloader").hide();
});

$(function () {
    $('#valorProduto').maskMoney({
        prefix: 'R$ ',
        thousands: '.',
        decimal: ','
    });
})

$(function () {
    $('#desconto').on('input', function () {
        let valor = $(this).val();
        valor = valor.replace(/[^\d]/g, '');

        let numero = parseInt(valor);

        if (isNaN(numero) || numero < 0) {
            numero = 0;
        } else if (numero > 100) {
            numero = 100;
        }

        $(this).val(numero);
    });
});

$(function () {
    const limiteCaracteres = 200; 

    $('#caracteristicas').on('input', function () {
        let texto = $(this).val();

        if (texto.length > limiteCaracteres) {
            texto = texto.substring(0, limiteCaracteres);

            $(this).val(texto);
            alert('Limite de texto atingido');
        }
    });

    $('#caracteristicas').on('paste', function (e) {
        setTimeout(function () {
            let texto = $('#meuTextarea').val();
            if (texto.length > limiteCaracteres) {
                texto = texto.substring(0, limiteCaracteres);

                $('#caracteristicas').val(texto);
            }
        }, 0);
    });
});



function verificarSeDeveAbrirAbaSalvar() {
    let url = new URL(window.location.href);
    let idProduto = url.searchParams.get("idProduto");

    let urlPura = url.toString().split('?')[0]

    if (idProduto != null && idProduto != '' && urlPura.endsWith('/produto')) {
        var encapuladorIdProduto = $("<button>", {
            id: idProduto
        });

        editar(encapuladorIdProduto);
    }
}

function configurarDadosAbaSalvar() {
    const formatter = new Intl.NumberFormat('pt-BR', {
        currency: 'BRL',
        minimumFractionDigits: 2
    });

    // Adapte o ID do campo de salário conforme necessário
    $("#valorProduto").val("R$ " + formatter.format($("#valorProduto").val()));

    let imgString = $('#imagemArmazenada').val();

    if (imgString != '') {
        $('#box-img').attr('src', imgString);
    } else {
        $('#box-img').attr('src', '/projeto-techno-fusion/view/admin/assets/img/produto.jpg');
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

/*################### Eventos e funções para os botões #######################*/
function enviarForm() {
    var formularioValido = validarCamposRequired();

    if (formularioValido) {
        $('#formProduto').submit();
    } else {
        alert('Por favor, preencha todos os campos obrigatórios.');
    }
}

function validarCamposRequired() {
    var camposValidos = true;
    $('#formProduto [required]').each(function () {
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

function previewImage() {
    var fileInput = document.getElementById('input-imagem');
    var imagePreview = document.getElementById('box-img');

    if (fileInput.files.length > 0) {
        var file = fileInput.files[0];

        var tamanhoArquivo = file.size; // Tamanho em bytes
        var tamanhoMaximo = 3 * 1024 * 1024; // 3 MB em bytes

        if (tamanhoArquivo > tamanhoMaximo) {
            alert("O tamanho da imagem deve ser no máximo de 3 MB");
            fileInput.value = '';
        } else {
            var reader = new FileReader();

            reader.onload = function (e) {
                imagePreview.src = e.target.result;
            };

            reader.readAsDataURL(file);
        }

    } else {
        imagePreview.src = '/projeto-techno-fusion/view/admin/assets/img/produto.jpg';
    }
}

function editar(e) {
    $("#box-msg").remove();
    $("#preloader").show();

    var formulario = document.getElementById('formProduto');
    var action = formulario.action;
    var id = $(e).attr('id');

    $.ajax({
        method: "get",
        url: action + "/editar",
        data: "id=" + id,
        success: function (response) {
            configDadosEdicao(response);

            setTabEdicao();
        },
        timeout: 10000
    }).done(function () {
        $("#preloader").hide();
    }).fail(function (xhr, status, errorThrown) {
        $("#preloader").hide();

        if (xhr.status === 401) {
            let url = $('#formProduto').attr('action');
            url = url.replace("produto", "login");

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
    let resultado = confirm("Deseja realmente excluir o produto #" + id + " ?");

    if (resultado) {
        let url = $('#formProduto').attr('action') + "/excluir?id=" + id;

        window.location.href = url;
    }
}

function configDadosEdicao(json) {
    configTabAtualizar();
    $('#id').val(json.id);
    $('#descricao').val(json.descricao);

    let formatter = new Intl.NumberFormat('pt-BR', {
		currency: 'BRL',
		minimumFractionDigits: 2
	});

    $("#valorProduto").val("R$ " + formatter.format(json.valor));
    $('#desconto').val(json.desconto);

    if (json.imagem != null && json.imagem != '') {
        $('#box-img').attr('src', json.imagem);
        $('#imagemArmazenada').val(json.imagem);

        let btnDownloadNaoExiste = $("#btnDownloadImagem").length < 1;

        if (btnDownloadNaoExiste) {
            let url = $('#formProduto').attr('action') + "/downloadImagem?idProduto=" + json.id;

            $('#groupPreviewImagem').append(`
                <span class="mt-2 mb-0" id="groupBtnDownloadImagem">
                    <a href=${url}>
                        <button type="button" id="btnDownloadImagem" class="btn btn-sm btn-dark me-2">Download</button>
                    </a>
                </span>`
            );
        }

    } else {
        $('#box-img').attr('src', '/projeto-techno-fusion/view/admin/assets/img/produto.jpg');
    }

    $('#marca').val(json.marca.id);
    $('#modelo').val(json.modelo);
    $('#caracteristicas').val(json.caracteristicas);

    let btnExcluirNaoExiste = $("#btnExcluirProduto").length < 1;

    if (btnExcluirNaoExiste) {
        $('#btn-acoes-form').append(`<button id="btnExcluirProduto" type="button" class="btn btn-danger me-2"
                                    onclick="excluir(${json.id})">Excluir</button>`);
    }
}

function configTabAtualizar() {
    $('#titulo-tab').text("Atualizar produto");
    $('#info-tab').text("Informe os novos dados do produto para atualizá-lo no sistema");
    $('#nav-profile-tab').text("Atualizar");
    $('#groupSenha').css("display", "none");
}

function setTabEdicao() {
    const triggerEl = document.querySelector('#nav-profile-tab');
    bootstrap.Tab.getInstance(triggerEl).show();
    $('#descricao').focus();
}

function restaurarTab() {
    $('#formProduto :input:not(:button, :submit)').val('');
    $("#marca option:first").prop("selected", true);
    $('#descricao').focus();

    $('#titulo-tab').text("Novo produto");
    $('#info-tab').text("Informe os dados do produto para cadastrá-lo no sistema");
    $('#nav-profile-tab').text("Cadastrar");

    $('#box-img').attr('src', '/projeto-techno-fusion/view/admin/assets/img/produto.jpg');
    $('#input-imagem').val("");
    $('#groupBtnDownloadImagem').remove();
    $('#btnExcluirProduto').remove();

}

function ativarBtnPaginaAtual() {
    $('ul-menu > li > a').removeClass('active');
    $('#btnPaginaProdutos').addClass('active');
}
