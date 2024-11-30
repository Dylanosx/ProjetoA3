
    async function generateTicket() {
        const placa = document.getElementById("placa").value;
        const modelo = document.getElementById("modelo").value;

        if (!placa || !modelo) {
            alert("Por favor, preencha todos os campos!");
            return;
        }

        const ticketData = {
            placa: placa,
            modeloVeiculo: modelo
        };

        try {
            const response = await fetch("http://localhost:8080/entrada", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(ticketData),
            });

            if (!response.ok) {
                const errorData = await response.json();
                alert(`Erro ao registrar: ${errorData.mensagem}`);
                return;
            }

            const result = await response.json();
            alert(`Sucesso: ${result.mensagem}\nVeículo: ${result.veiculo}\nHora Entrada: ${result.horaEntrada}`);
            
            // Limpando o formulário após registro
            document.getElementById("vehicleForm").reset();
        } catch (error) {
            console.error("Erro ao conectar com o backend:", error);
            alert("Erro ao conectar com o servidor. Tente novamente.");
        }
    }
    
