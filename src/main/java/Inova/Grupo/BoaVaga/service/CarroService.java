package Inova.Grupo.BoaVaga.service;

import Inova.Grupo.BoaVaga.model.Carro;
import Inova.Grupo.BoaVaga.repository.CarroRepository;
import org.springframework.stereotype.Service;

@Service
public class CarroService {

    private final CarroRepository carroRepository;

    public CarroService(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    /**
     * Busca um carro pela placa. Se não existir, cria um novo registro.
     * @param placa A placa do carro.
     * @param modelo O modelo do carro (usado se for necessário criar).
     * @return O carro encontrado ou o novo carro criado.
     */
    public Carro encontrarOuCriar(String placa, String modelo) {
        return carroRepository.findById(placa).orElseGet(() -> {
            Carro novoCarro = new Carro();
            novoCarro.setPlaca(placa);
            novoCarro.setModelo(modelo);
            // Em um cenário real, você poderia associar um motorista aqui.
            return carroRepository.save(novoCarro);
        });
    }
}