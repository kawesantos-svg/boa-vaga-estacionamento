package Inova.Grupo.BoaVaga.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "carros")
@Data
public class Carro {
    @Id // A placa pode ser a chave primária natural
    @Column(unique = true, nullable = false)
    private String placa;

    @Column(nullable = false)
    private String modelo;

    private String cor;

    // Relacionamento com o motorista
    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Motorista proprietario;
}