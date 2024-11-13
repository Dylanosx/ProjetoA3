package br.anhembi.projetoa324.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.anhembi.projetoa324.model.Pagamento;
import br.anhembi.projetoa324.model.Ticket;
import br.anhembi.projetoa324.repository.PagamentoRepo;
import br.anhembi.projetoa324.repository.TicketRepo;

@Service
public class EntradaService {

    @Autowired
    private TicketRepo ticketRepo;

    @Autowired
    private PagamentoRepo pagamentoRepo;

    public Ticket criarTicket(String placa, String modeloCarro){
        Ticket ticket = new Ticket();
        ticket.setPlaca(placa);
        ticket.setModeloCarro(modeloCarro);
        ticket.setStatusPagamento("Pendente");
        return ticketRepo.save(ticket);
    }

    public Ticket buscarTicketPorPlaca(String placa){
        return ticketRepo.findByPlaca(placa);
    }

    public Pagamento processarPagamento(Long ticketId, double valor){
        Ticket ticket = ticketRepo.findById(ticketId).orElse(null);


        if(ticket !=null && "Pendente".equals(ticket.getStatusPagamento())){
            ticket.setStatusPagamento("Pago");
            ticketRepo.save(ticket);

            /*registrar o pagamento */
            Pagamento pagamento = new Pagamento();
            pagamento.setTicketId(ticketId);
            BigDecimal valorPago = BigDecimal.valueOf(valor);
            pagamento.setValorPago(valorPago);
            return pagamentoRepo.save(pagamento);
        } return null;
    }
}
