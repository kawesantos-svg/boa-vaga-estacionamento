package Inova.Grupo.BoaVaga.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "motoristas")
@Data
public class Motorista {
        @Id
        @GeneratedValue
        private UUID id;

        @Column(nullable = false)
        private String nome;

        @Column(nullable = false, unique = true)
        private String cpf; // Um bom identificador único para o motorista

        // Pode ter uma lista de carros
        @JsonIgnore
        @OneToMany(mappedBy = "proprietario")
        private List<Carro> carros;
}


