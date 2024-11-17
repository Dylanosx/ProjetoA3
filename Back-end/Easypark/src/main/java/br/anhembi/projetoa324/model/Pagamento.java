package br.anhembi.projetoa324.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @Column(name = "valor_pago", nullable = false)
    private BigDecimal valorPago;
    
    @ManyToOne
    @JoinColumn(name = "ticket_ref_id", nullable = false, referencedColumnName = "id")
    private Ticket ticket;
    
    public Long getTicketId() {
        return ticketId;
    }
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }
    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public Ticket getTicket() {
        return ticket;
    }
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString(){
        return "Pagamento [Valor pago = " + valorPago + ", Ticket id=" + ticket.getId() + "]";
    }
    
}