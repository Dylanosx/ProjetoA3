package br.anhembi.projetoa324.repository;

import org.springframework.data.repository.CrudRepository;

import br.anhembi.projetoa324.model.Ticket;

public interface TicketRepo extends CrudRepository<Ticket, Long> {
    Ticket findByPlaca(String placa);
}
