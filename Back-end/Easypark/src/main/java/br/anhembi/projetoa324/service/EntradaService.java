package br.anhembi.projetoa324.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

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

    public Ticket criarTicket(String placa, String modeloVeiculo){
        Ticket ticket = new Ticket();
        ticket.setPlaca(placa);
        ticket.setModeloVeiculo(modeloVeiculo);
        ticket.setHoraEntrada(LocalDateTime.now());
        ticket.setStatusPagamento("Pendente");
        return ticketRepo.save(ticket);
    }
    
    public Ticket registrarSaida(String placa){
        Ticket ticket = ticketRepo.findByPlaca(placa);

        if(ticket == null){
            throw new IllegalArgumentException("Ticket não encontrado para a placa: " + placa);
        }
        if (ticket.getHoraSaida() != null) {
            throw new IllegalStateException("Saída já registrada para o ticket da placa: " + placa);
        }

        //capturar a hora de saida do estacionamento
        ticket.setHoraSaida(LocalDateTime.now());

        //calcular o valor com base no tempo de permanencia no estacionamento
        long minutos = Duration.between(ticket.getHoraEntrada(), ticket.getHoraSaida()).toMinutes();
        double valor = calcularValor(minutos);

        ticket.setValorPago(valor);
        ticket.setStatusPagamento("pendente");

        return ticketRepo.save(ticket);
    }


    private double calcularValor(long minutos){
        //cobranca
        return minutos * 0.50;
    }

    public Ticket buscarTicketPorPlaca(String placa){
        return ticketRepo.findByPlaca(placa);
    }

    //processamento do pagamento, mudanca do status para pago e registro do valor
 public Pagamento processarPagamento(Long ticketId, double valor){
        Ticket ticket = ticketRepo.findById(ticketId).orElse(null);

        if(ticket == null){
           throw new IllegalArgumentException("Ticket não encontrado");
        }
        if(!"Pendente".equals(ticket.getStatusPagamento())){
            throw new IllegalStateException("");
        }
        
        //atualizar status pagamento
        ticket.setStatusPagamento("pago");
        ticketRepo.save(ticket);

        //registrar o pagamento
        Pagamento pagamento =  new Pagamento();
        pagamento.setTicketId(ticketId);
        pagamento.setValorPago(BigDecimal.valueOf(valor));
        return pagamentoRepo.save(pagamento);
    }
}
