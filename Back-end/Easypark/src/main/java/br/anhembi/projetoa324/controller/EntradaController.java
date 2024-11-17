package br.anhembi.projetoa324.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import br.anhembi.projetoa324.model.Pagamento;
import br.anhembi.projetoa324.model.Ticket;
import br.anhembi.projetoa324.service.EntradaService;

@RestController
@RequestMapping
public class EntradaController {
    @Autowired
    private EntradaService ticketService;

    @PostMapping("/entrada")
    public Ticket criarTicket(@RequestBody Ticket ticket){
        return ticketService.criarTicket(ticket.getPlaca(), ticket.getModeloVeiculo());
    }

    @GetMapping("/consulta")
    public Ticket consultarTicket(@RequestParam String placa){
        return ticketService.buscarTicketPorPlaca(placa);

    }

    @PostMapping("/pagamento")
    public Pagamento processPagamento(@RequestParam Long ticketId, @RequestParam double valor){
        return ticketService.processarPagamento(ticketId, valor);
    }
}
