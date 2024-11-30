

function generateTicket() {
    const placa = document.getElementById('placa').value;
    const modelo = document.getElementById('modelo').value;

    
    if (placa && modelo) {
        localStorage.setItem('placa', placa);
        fetch('http://localhost:8080/entrada', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                placa: placa,
                modeloVeiculo: modelo
            })
        })
        .then(response => {
            if (response.ok) {
                return response.json().then(data => {
                    // Display the success modal
                    $('#ticketModal').modal('show');
                });
            } else {
                return response.json().then(data => {
                    alert('Erro: ' + (data.mensagem || 'Ocorreu um erro.'));
                });
            }
        })
        .catch(error => {
            console.error('Erro ao gerar ticket:', error);
            alert('Erro ao se comunicar com o servidor.');
        });
    } else {
        alert('Por favor, preencha todos os campos!');
    }
}

