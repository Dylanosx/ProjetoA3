package br.anhembi.projetoa324.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valorPago;
    private Long ticketId;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public BigDecimal getValorPago() {
        return valorPago;
    }
    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }
    public Long getTicketId() {
        return ticketId;
    }
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String toString(){
        return "Pagamento [Valor pago = " + valorPago + ", Ticket id=" + ticketId + "]";
    }
    
}