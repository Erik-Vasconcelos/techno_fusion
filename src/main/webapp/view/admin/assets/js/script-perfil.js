document.addEventListener('DOMContentLoaded', function () {
    formatarDados();

    var statusResposta = $('#statusResposta').val();

    if (statusResposta === 'ERROR') {
        habilitarCampos();
        $("#senha").focus();
    }
});

function habilitarCampos() {
    $("#senha").val("");
    $("#senha").prop("readonly", false);

    $("#groupConfirmSenha").show();
    $("#groupButton").show();
}

function desabilitarCampos() {
    $("#senha").prop("readonly", true);
    $("#senha").val("********");
    $("#confirmSenha").val("");

    $("#groupConfirmSenha").hide();
    $("#groupButton").hide();

    $('#statusResposta').val("");
}

function formatarDados() {
    var date = new Date($('#dataNascimento').text());
    var day = ("0" + date.getDate()).slice(-2);
    var month = ("0" + (date.getMonth() + 1)).slice(-2);
    var dateString = (day) + "/" + (month) + "/" + date.getFullYear();

    $('#dataNascimento').text(dateString);

    const formatter = new Intl.NumberFormat('pt-BR', {
        currency: 'BRL',
        minimumFractionDigits: 2
    });

    $("#salario").text("R$ " + formatter.format($("#salario").text()));

    $("#groupTelefones span.text-dark.text-sm").each(function () {
        var numeroTelefone = $(this).text().trim();
        $(this).text(formatarNumeroTelefone(numeroTelefone));
    });
}

function formatarNumeroTelefone(value) {
    if (!value) return "";
    value = value.replace(/\D/g, '');

    value = value.slice(0, 11);
    value = value.replace(/(\d{2})(\d)/, "($1) $2")
    value = value.replace(/(\d)(\d{4})$/, "$1-$2")

    return value;
}

function enviarForm() {
    var camposPreenchidos = validarCamposRequired();

    if (camposPreenchidos) {
        var senha = $("#senha").val();
        var confirmSenha = $("#confirmSenha").val();

        if (senha.length >= 8) {
            if (senha === confirmSenha) {
                $('#formCredenciais').submit();
            } else {
                alert("Senhas não coincidem. Por favor, verifique.");
            }
        } else {
            alert("A senha deve ter no mínimo 8 caracteres.");
        }
    } else {
        alert('Por favor, preencha todos os campos obrigatórios.');
    }
}


function validarCamposRequired() {
    var camposValidos = true;
    $('#formCredenciais [required]').each(function () {
        var valorCampo = $(this).val();
        if (!valorCampo.trim()) {
            camposValidos = false;
            return false; // Sair do loop
        }
    });

    return camposValidos;
}