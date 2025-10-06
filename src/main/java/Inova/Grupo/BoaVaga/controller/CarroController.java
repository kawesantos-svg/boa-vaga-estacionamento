// src/main/java/Inova/Grupo/BoaVaga/controller/CarroController.java
package Inova.Grupo.BoaVaga.controller;

import Inova.Grupo.BoaVaga.dto.CarroDTO;
import Inova.Grupo.BoaVaga.model.Carro;
import Inova.Grupo.BoaVaga.service.CarroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/carros")
public class CarroController {
    private final CarroService carroService;

    public CarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @GetMapping
    public ResponseEntity<List<Carro>> listarTodos() {
        return ResponseEntity.ok(carroService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Carro> criarCarro(@Valid @RequestBody CarroDTO carroDTO) {
        Carro novoCarro = new Carro();
        novoCarro.setPlaca(carroDTO.getPlaca());
        novoCarro.setModelo(carroDTO.getModelo());
        novoCarro.setCor(carroDTO.getCor());

        Carro carroSalvo = carroService.criar(novoCarro, carroDTO.getProprietarioId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{placa}")
                .buildAndExpand(carroSalvo.getPlaca()).toUri();

        return ResponseEntity.created(location).body(carroSalvo);
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<Void> deletarCarro(@PathVariable String placa) {
        carroService.deletar(placa);
        return ResponseEntity.noContent().build();
    }
}