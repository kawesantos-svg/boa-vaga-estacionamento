package Inova.Grupo.BoaVaga.dto;

import Inova.Grupo.BoaVaga.model.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagamentoDTO {

    private BigDecimal valor;
    private LocalDateTime dataPagamento;
    private String placaCarro;
    private LocalDateTime horarioEntrada;
    private LocalDateTime horarioSaida;

    // Construtor para facilitar a conversão
    public PagamentoDTO(Pagamento pagamento) {
        this.valor = pagamento.getValor();
        this.dataPagamento = pagamento.getDataPagamento();
        this.placaCarro = pagamento.getEstadia().getCarro().getPlaca();
        this.horarioEntrada = pagamento.getEstadia().getHorarioEntrada();
        this.horarioSaida = pagamento.getEstadia().getHorarioSaida();
    }

    // Getters e Setters
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getPlacaCarro() {
        return placaCarro;
    }

    public void setPlacaCarro(String placaCarro) {
        this.placaCarro = placaCarro;
    }

    public LocalDateTime getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(LocalDateTime horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public LocalDateTime getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(LocalDateTime horarioSaida) {
        this.horarioSaida = horarioSaida;
    }
}