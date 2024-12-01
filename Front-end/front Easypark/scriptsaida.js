function showDetails() {
    const placa = localStorage.getItem('placa');

    if(!placa){
        alert("Placa não encontrada");
        return;
    }

    if (placa.trim() === "") {
        alert("Por favor, insira o número da placa.");
        return;
    }

    fetch(`http://localhost:8080/consulta?placa=${placa}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao consultar ticket");
        }
        return response.json();
    })
        .then(data => {
            // Processa o horário de entrada
            let entradaParts = data.horarios.split(",")[0].split(":");
            document.getElementById("entrada").textContent = entradaParts[1].trim() + ":" + entradaParts[2].trim();

            // Processa o horário de saída
            let saidaParts = data.horarios.split(",")[1].split(":");
            document.getElementById("saida").textContent = saidaParts[1].trim() + ":" + saidaParts[2].trim();

            // Processa o valor
            document.getElementById("valor").textContent = data.pagamento.split(",")[1].split(":")[1].trim();

            // Exibe o modal com os detalhes
            $('#detailsModal').modal('show');
        })
        .catch(error => {
            console.error("Erro:", error);
            alert("Não foi possível consultar o ticket.");
        });
}
