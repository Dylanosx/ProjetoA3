package br.anhembi.projetoa324.controller;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import br.anhembi.projetoa324.service.EntradaService;
import br.anhembi.projetoa324.model.Ticket;

@RestController
@RequestMapping
public class EntradaController {
    @Autowired
    private EntradaService ticketService;


    @PostMapping("/entrada")
    public ResponseEntity<Map<String, String>> criarTicket(@RequestBody Ticket ticket) {
  
        ticket.setStatusPagamento("Pendente");
    
        // cria o ticket
        Ticket novoTicket = ticketService.criarTicket(ticket.getPlaca(), ticket.getModeloVeiculo());
    
        // formatar a resposta para o cliente
        Map<String, String> response = Map.of(
            "mensagem", "Entrada registrada com sucesso!",
            "veiculo", novoTicket.getModeloVeiculo() + " - " + novoTicket.getPlaca(),
            "horaEntrada", novoTicket.getHoraEntrada().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }    
    
    @GetMapping("/consulta") //quando o clinte sair do estacionamento consulta a placa para saber seu tempo de permanencia e valor a pagar
    public ResponseEntity<Map<String, String>> consultarTicket(@RequestParam String placa) {
        Ticket ticket = ticketService.buscarTicketPorPlaca(placa);
        if (ticket == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensagem", "Ticket não encontrado para a placa: " + placa));
        }
    
        if (ticket.getHoraSaida() == null) {
            try {
                ticket = ticketService.registrarSaida(placa);
            } catch (IllegalStateException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensagem", e.getMessage()));
            }
        }
    
        // calcular tempo de permanencia
        String tempoPermanencia = ticketService.calcularTempoDePermanencia(ticket);
    
        //retornar mensagens
        Map<String, String> response = Map.of(
            "veiculo", ticket.getModeloVeiculo() + " " + ticket.getPlaca(),
            "horarios", String.format("Entrada: %s, Saída: %s, Permanência: %s",
                    ticket.getHoraEntrada().format(DateTimeFormatter.ofPattern("HH:mm")),
                    ticket.getHoraSaida().format(DateTimeFormatter.ofPattern("HH:mm")),
                    tempoPermanencia),
            "pagamento", String.format("Pagamento: %s, Valor: R$%.2f",
                    ticket.getStatusPagamento(),
                    ticket.getValorPago())
        );
        return ResponseEntity.ok(response);
    }

            @GetMapping("/metodos-pagamento")
            public String exibirMetodosPagamento() {
                return "metodos_pagamento.html";
            }
        
            @GetMapping("/pix-pagamento")
            public String exibirPixPagamento() {
                return "pix_pagamento.html";
            }
        
            @GetMapping("/cartao-pagamento")
            public String exibirCartaoPagamento() {
                return "cartao_pagamento.html";
            }
}

    
    
 

