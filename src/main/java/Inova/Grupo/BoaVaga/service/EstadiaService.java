package Inova.Grupo.BoaVaga.service;

import Inova.Grupo.BoaVaga.model.*;
import Inova.Grupo.BoaVaga.repository.EstadiaRepository;
import Inova.Grupo.BoaVaga.repository.PagamentoRepository;
import Inova.Grupo.BoaVaga.repository.VagaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class EstadiaService {

    private final EstadiaRepository estadiaRepository;
    private final VagaRepository vagaRepository;
    private final CarroService carroService; // Usando o CarroService
    private final PagamentoRepository pagamentoRepository;

    public EstadiaService(EstadiaRepository estadiaRepository, VagaRepository vagaRepository, CarroService carroService, PagamentoRepository pagamentoRepository) {
        this.estadiaRepository = estadiaRepository;
        this.vagaRepository = vagaRepository;
        this.carroService = carroService;
        this.pagamentoRepository = pagamentoRepository;
    }

    /**
     * Registra a entrada de um veículo no estacionamento.
     * @param placa A placa do veículo.
     * @param modelo O modelo do veículo.
     * @param numeroVaga O número da vaga a ser ocupada.
     * @return A Estadia criada.
     */
    public Estadia registrarEntrada(String placa, String modelo, String numeroVaga) {
        // 1. Encontra ou cria o carro
        Carro carro = carroService.encontrarOuCriar(placa, modelo);

        // 2. Verifica se o carro já tem uma estadia ativa
        if (estadiaRepository.findByCarroAndAtivaTrue(carro).isPresent()) {
            throw new RuntimeException("Este veículo já está no estacionamento.");
        }

        // 3. Encontra a vaga e verifica se está disponível
        Vaga vaga = vagaRepository.findByNumeroVaga(numeroVaga)
                .orElseThrow(() -> new RuntimeException("Vaga não encontrada."));

        if (vaga.getStatus() == StatusVaga.OCUPADA) {
            throw new RuntimeException("Vaga já está ocupada.");
        }

        // 4. Cria a estadia
        Estadia novaEstadia = new Estadia();
        novaEstadia.setCarro(carro);
        novaEstadia.setVaga(vaga);
        novaEstadia.setHorarioEntrada(LocalDateTime.now());
        novaEstadia.setAtiva(true);

        // 5. Atualiza o status da vaga
        vaga.setStatus(StatusVaga.OCUPADA);
        vagaRepository.save(vaga);

        return estadiaRepository.save(novaEstadia);
    }

    /**
     * Registra a saída de um veículo e calcula o valor a ser pago.
     * @param estadiaId O ID da estadia a ser finalizada.
     * @return O Pagamento gerado.
     */
    public Pagamento registrarSaida(UUID estadiaId) {
        // 1. Encontra a estadia ativa
        Estadia estadia = estadiaRepository.findById(estadiaId)
                .orElseThrow(() -> new RuntimeException("Estadia não encontrada."));

        if (!estadia.isAtiva()) {
            throw new RuntimeException("Esta estadia já foi finalizada.");
        }

        // 2. Define os dados de saída
        estadia.setHorarioSaida(LocalDateTime.now());
        estadia.setAtiva(false);

        // 3. Calcula o valor
        BigDecimal valorAPagar = calcularValor(estadia.getHorarioEntrada(), estadia.getHorarioSaida());

        // 4. Cria o registro de pagamento
        Pagamento pagamento = new Pagamento();
        pagamento.setEstadia(estadia);
        pagamento.setValor(valorAPagar);
        pagamento.setDataPagamento(LocalDateTime.now());
        // A forma de pagamento poderia vir da requisição do controller
        pagamento.setFormaPagamento(FormaPagamento.PIX);
        pagamentoRepository.save(pagamento);

        // 5. Libera a vaga
        Vaga vaga = estadia.getVaga();
        vaga.setStatus(StatusVaga.DISPONIVEL);
        vagaRepository.save(vaga);

        estadiaRepository.save(estadia);
        return pagamento;
    }

    private BigDecimal calcularValor(LocalDateTime entrada, LocalDateTime saida) {
        Duration duration = Duration.between(entrada, saida);
        long hours = duration.toHours();

        // Se houver minutos restantes, cobra a hora cheia
        if (duration.toMinutes() % 60 != 0) {
            hours++;
        }

        // Se o tempo for menor que uma hora, cobra uma hora
        if (hours == 0) {
            hours = 1;
        }

        // Lógica de preço: R$5 fixo + R$2 por hora
        BigDecimal taxaFixa = new BigDecimal("5.00");
        BigDecimal taxaPorHora = new BigDecimal("2.00");
        return taxaFixa.add(taxaPorHora.multiply(new BigDecimal(hours)));
    }
}