package br.anhembi.projetoa324.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@SpringBootTest
public class tickettest {

    @Test
    void testGettersAndSetters() {
        Ticket ticket = new Ticket();

        // Teste do ID
        ticket.setId(1L);
        assertThat(ticket.getId()).isEqualTo(1L);

        // Teste do modelo do veículo
        ticket.setModeloVeiculo("Sedan");
        assertThat(ticket.getModeloVeiculo()).isEqualTo("Sedan");

        // Teste da placa
        ticket.setPlaca("ABC-1234");
        assertThat(ticket.getPlaca()).isEqualTo("ABC-1234");

        // Teste do status de pagamento
        ticket.setStatusPagamento("Pago");
        assertThat(ticket.getStatusPagamento()).isEqualTo("Pago");

        // Teste da hora de entrada
        LocalDateTime entrada = LocalDateTime.of(2024, 11, 30, 14, 0);
        ticket.setHoraEntrada(entrada);
        assertThat(ticket.getHoraEntrada()).isEqualTo(entrada);

        // Teste da hora de saída
        LocalDateTime saida = LocalDateTime.of(2024, 11, 30, 16, 0);
        ticket.setHoraSaida(saida);
        assertThat(ticket.getHoraSaida()).isEqualTo(saida);

        // Teste do valor pago
        ticket.setValorPago(50.0);
        assertThat(ticket.getValorPago()).isEqualTo(50.0);
    }

    @Test
    void testDefaultStatusPagamento() {
        // Testando o valor padrão de "statusPagamento"
        Ticket ticket = new Ticket();
        assertThat(ticket.getStatusPagamento()).isEqualTo("Pendente");
    }

    @Test
    void testToString() {
        Ticket ticket = new Ticket();
        ticket.setModeloVeiculo("SUV");
        ticket.setPlaca("XYZ-9876");

        String expected = "Ticket [Modelo do Veiculo=SUV, Placa=XYZ-9876, Status do pagamento =Pendente]";
        assertThat(ticket.toString()).isEqualTo(expected);
    }

    @Test
    void testHoraEntradaImutabilidade() {
        Ticket ticket = new Ticket();
        LocalDateTime entrada = LocalDateTime.now();
        ticket.setHoraEntrada(entrada);

        // Verificar se a hora de entrada permanece como foi definida
        assertThat(ticket.getHoraEntrada()).isEqualTo(entrada);
    }
}
