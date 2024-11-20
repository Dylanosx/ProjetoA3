package br.anhembi.projetoa324.controller;

import java.net.URI;
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

    @PostMapping("/pagamento")
    public ResponseEntity<Map<String, String>> processarPagamento(@RequestParam String placa, @RequestParam String metodoPagamento) {
        try {
            Ticket ticket = ticketService.buscarTicketPorPlaca(placa);

            if (ticket == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensagem", "Ticket não encontrado para a placa " + placa));
            }

            if ("pix".equalsIgnoreCase(metodoPagamento)) {
                // leva o cliente para a pagina de pagamento do pix
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create("/pix_pagamento.html?valor=" + ticket.getValorPago()))
                        .build();
            } else if ("cartao".equalsIgnoreCase(metodoPagamento)) {
                // leva o cliente para a pagina de pagamento do cartao
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create("/cartao_pagamento.html?valor=" + ticket.getValorPago()))
                        .build();
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("mensagem", "Método de pagamento não suportado"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensagem", "Erro ao processar pagamento. Tente novamente."));
        }
    }


}

    
    
 

