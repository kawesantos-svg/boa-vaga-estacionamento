package Inova.Grupo.BoaVaga.controller;

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
    public ResponseEntity<Estadia> registrarEntrada(@RequestBody EstadiaCheckinDTO checkinDTO) {
        try {
            Estadia novaEstadia = estadiaService.registrarEntrada(
                    checkinDTO.getPlaca(),
                    checkinDTO.getModelo(),
                    checkinDTO.getNumeroVaga()
            );

            // Cria a URI para o novo recurso criado
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(novaEstadia.getId()).toUri();

            return ResponseEntity.created(location).body(novaEstadia);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Em uma app real, retorne o erro.
        }
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<PagamentoDTO> registrarSaida(@PathVariable UUID id) {
        try {
            Pagamento pagamento = estadiaService.registrarSaida(id);
            // Converte a entidade Pagamento para o DTO de resposta
            PagamentoDTO resposta = new PagamentoDTO(pagamento);
            return ResponseEntity.ok(resposta);
        } catch (RuntimeException e) {
            // Em uma aplicação real, você pode tratar exceções específicas
            return ResponseEntity.badRequest().body(null); // Retorne a mensagem de erro.
        }
    }
}