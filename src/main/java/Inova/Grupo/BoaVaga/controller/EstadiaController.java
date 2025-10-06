package Inova.Grupo.BoaVaga.controller;
import jakarta.validation.Valid;
import Inova.Grupo.BoaVaga.dto.EstadiaCheckinDTO;
import Inova.Grupo.BoaVaga.dto.PagamentoDTO;
import Inova.Grupo.BoaVaga.model.Estadia;
import Inova.Grupo.BoaVaga.model.Pagamento;
import Inova.Grupo.BoaVaga.service.EstadiaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/estadias")
public class EstadiaController {

    private final EstadiaService estadiaService;

    public EstadiaController(EstadiaService estadiaService) {
        this.estadiaService = estadiaService;
    }

    @PostMapping("/checkin")
    public ResponseEntity<Estadia> registrarEntrada(@Valid @RequestBody EstadiaCheckinDTO checkinDTO) {
        Estadia novaEstadia = estadiaService.registrarEntrada(
                checkinDTO.getPlaca(),
                checkinDTO.getModelo(),
                checkinDTO.getNumeroVaga()
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(novaEstadia.getId()).toUri();

        return ResponseEntity.created(location).body(novaEstadia);
    }

    @GetMapping("/vaga/{numeroVaga}")
    public ResponseEntity<Estadia> buscarPorNumeroVaga(@PathVariable String numeroVaga) {
        Estadia estadia = estadiaService.buscarAtivaPorNumeroVaga(numeroVaga);
        return ResponseEntity.ok(estadia);
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<PagamentoDTO> registrarSaida(@PathVariable UUID id) {
        Pagamento pagamento = estadiaService.registrarSaida(id);
        PagamentoDTO resposta = new PagamentoDTO(pagamento);
        return ResponseEntity.ok(resposta);
    }
}