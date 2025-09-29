package Inova.Grupo.BoaVaga.repository;

import Inova.Grupo.BoaVaga.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {

}