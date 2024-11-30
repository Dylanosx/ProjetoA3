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
    
    /**
     * @param placa
     * @return
     */
    public Ticket registrarSaida(String placa){
        var ticket = ticketRepo.findByPlaca(placa);

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

        ((Pagamento) ticket).setValorPago(valor);
        ticket.setStatusPagamento("pendente");

        return ticketRepo.save(ticket);
    }

    public String calcularTempoDePermanencia(Ticket ticket) {
        if (ticket.getHoraSaida() == null) {
            throw new IllegalStateException("Hora de saída não registrada para o ticket.");
        }
    
        long minutos = Duration.between(ticket.getHoraEntrada(), ticket.getHoraSaida()).toMinutes();
        long horas = minutos / 60;
        minutos %= 60;
    
        return String.format("%dh %dmin", horas, minutos);
    }
    
    private double calcularValor(long minutos){
        if (minutos <= 60) {
            return 5.00; // Até 1 hora
        } else if (minutos <= 120) {
            return 10.00; // 1 a 2 horas
        } else if (minutos <= 180) {
            return 15.00; // 2 a 3 horas
        } else if (minutos <= 1440) {
            return 25.00; // Diária
        }else{
        // se passar de 24 horas será cobrada uma diária adicional
        long diasAdicionais = (minutos - 1440) / 1440;
        if ((minutos - 1440) % 1440 > 0) {
            diasAdicionais++;
        }
        return 25.00 * (1 + diasAdicionais); // cobrar +25 reais por cada dia adicional
        }
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
