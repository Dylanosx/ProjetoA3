package br.anhembi.projetoa324.dto;
import java.math.BigDecimal;

public class PagamentoRequisicao{
    private String placa;
    private BigDecimal valorPagamento; 
    private String metodoPagamento; //cartap ou pix

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public BigDecimal getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPago(BigDecimal valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
}
