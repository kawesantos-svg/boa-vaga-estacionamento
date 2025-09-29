package Inova.Grupo.BoaVaga.repository;

import Inova.Grupo.BoaVaga.model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface MotoristaRepository extends JpaRepository<Motorista, UUID> {

    /**
     * Encontra um motorista pelo seu CPF.
     * @param cpf O CPF do motorista.
     * @return Um Optional contendo o Motorista se encontrado.
     */
    Optional<Motorista> findByCpf(String cpf);
}