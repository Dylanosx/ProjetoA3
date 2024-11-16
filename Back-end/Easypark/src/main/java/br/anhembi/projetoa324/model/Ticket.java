package br.anhembi.projetoa324.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * @author Pichau
 */
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String modeloCarro;

    @Column(nullable = false)
    private String placa;

    @Column(nullable = false, updatable = false)
    private LocalDateTime horaEntrada;

    private LocalDateTime horaSaida;
    
    private Double valorPago;

    private String statusPagamento = "Pendente";
    
    @Override
    public String toString() {
        return "Ticket [Modelo do Carro=" + modeloCarro + ", Placa=" + placa + ", Status do pagamento =" + statusPagamento + "]";
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setModeloCarro(String modeloCarro) {
        this.modeloCarro = modeloCarro;
    }
    
    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public String getStatusPagamento() {
        return statusPagamento;
    }
    
    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }
    
    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }
    
    public void setHoraSaida(LocalDateTime horaSaida) {
        this.horaSaida = horaSaida;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }
    public String getModeloCarro() {
        return modeloCarro;
    }  
    
}

