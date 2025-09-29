package Inova.Grupo.BoaVaga.repository;

import Inova.Grupo.BoaVaga.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;

// A chave primária da entidade Carro é a placa (String)
public interface CarroRepository extends JpaRepository<Carro, String> {

}