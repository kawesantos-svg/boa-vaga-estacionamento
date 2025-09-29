package Inova.Grupo.BoaVaga.repository;
import Inova.Grupo.BoaVaga.model.Vaga;
import Inova.Grupo.BoaVaga.model.StatusVaga;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VagaRepository extends JpaRepository<Vaga, UUID> {

    /**
     * Encontra uma vaga pelo número específico.
     * @param numeroVaga O número da vaga (ex: "A1").
     * @return Um Optional contendo a Vaga se encontrada.
     */
    Optional<Vaga> findByNumeroVaga(String numeroVaga);

    /**
     * Lista todas as vagas com um determinado status.
     * @param status O status da vaga (DISPONIVEL ou OCUPADA).
     * @return Uma lista de Vagas.
     */
    List<Vaga> findByStatus(StatusVaga status);
}