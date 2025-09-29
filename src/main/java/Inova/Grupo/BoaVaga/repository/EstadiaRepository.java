package Inova.Grupo.BoaVaga.repository;

import Inova.Grupo.BoaVaga.model.Estadia;
import Inova.Grupo.BoaVaga.model.Carro;
import Inova.Grupo.BoaVaga.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface EstadiaRepository extends JpaRepository<Estadia, UUID> {

    /**
     * Encontra a estadia ativa para uma vaga específica.
     * Útil para operações de checkout.
     * @param vaga A vaga que está ocupada.
     * @return Um Optional contendo a Estadia ativa se houver.
     */
    Optional<Estadia> findByVagaAndAtivaTrue(Vaga vaga);

    /**
     * Encontra a estadia ativa para um carro específico.
     * Útil para verificar se um carro já está estacionado.
     * @param carro O carro a ser verificado.
     * @return Um Optional contendo a Estadia ativa se houver.
     */
    Optional<Estadia> findByCarroAndAtivaTrue(Carro carro);
}