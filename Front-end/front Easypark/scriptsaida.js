// Função chamada quando o botão "Confirmar" é pressionado
function showDetails() {
    // Captura o valor da placa inserido pelo usuário
    const placa = document.getElementById("placa").value;

    // Verifica se o campo placa está vazio
    if (placa.trim() === "") {
        alert("Por favor, insira o número da placa.");
        return;
    }

    // Faz a requisição para o backend usando o valor da placa
    fetch('http://localhost:3306/consulta?placa=${placa}')

        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao consultar ticket");
            }
            return response.json();
        })
        .then(data => {
            // Preenche os campos do modal com as informações retornadas
            document.getElementById("entrada").textContent = data.horarios.split(",")[0].split(":")[1].trim();
            document.getElementById("saida").textContent = data.horarios.split(",")[1].split(":")[1].trim();
            document.getElementById("valor").textContent = data.pagamento.split(",")[1].split(":")[1].trim();

            // Exibe o modal com os detalhes
            $('#detailsModal').modal('show');
        })
        .catch(error => {
            // Exibe uma mensagem de erro caso algo falhe
            console.error("Erro:", error);
            alert("Não foi possível consultar o ticket.");
        });
}

// Função para redirecionar para a página de pagamento
function goToPayment() {
    window.location.href = "metodos_pagamento.html";
}
