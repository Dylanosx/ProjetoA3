async function showDetails() {
    const placa = document.getElementById("placa").value.trim();

    if (placa === "") {
        alert("Por favor, insira a placa do veículo.");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/consulta?placa=${placa}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });

        if (!response.ok) {
            const errorData = await response.json();
            alert(errorData.mensagem || "Erro ao buscar o ticket.");
            return;
        }

        const ticketData = await response.json();

        // Preenche os dados no modal
        document.getElementById("entrada").textContent = ticketData.horarios.split(",")[0].split(":")[1].trim();
        document.getElementById("saida").textContent = ticketData.horarios.split(",")[1].split(":")[1].trim();
        document.getElementById("valor").textContent = ticketData.pagamento.split(",")[1].split(":")[1].trim();

        // Exibe o modal
        $('#detailsModal').modal('show');
    } catch (error) {
        console.error("Erro ao consultar o ticket:", error);
        alert("Erro ao consultar o ticket. Por favor, tente novamente.");
    }
}
