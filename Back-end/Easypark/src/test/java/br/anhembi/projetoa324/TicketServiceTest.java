package br.anhembi.projetoa324;
import br.anhembi.projetoa324.model.Ticket;
import br.anhembi.projetoa324.service.EntradaService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TicketServiceTest{

    @Autowired
    private EntradaService ticketService;

    @Test
    public void testCriarTicket(){
    Ticket ticket = ticketService.criarTicket("ABC-1234", "Fusca");

    assertNotNull(ticket);
    assertEquals("ABC-1234", ticket.getPlaca());
    assertEquals("Fusca", ticket.getModeloCarro());
    assertEquals("Pendente", ticket.getStatusPagamento());
        
    }
}

