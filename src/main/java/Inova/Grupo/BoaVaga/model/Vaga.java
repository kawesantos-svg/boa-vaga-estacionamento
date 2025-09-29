package Inova.Grupo.BoaVaga.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vagas")
@Data
public class Vaga {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String numeroVaga; // Ex: A1, B2

    // Novo campo para controlar o status da vaga
    @Enumerated(EnumType.STRING)
    private StatusVaga status; // Enum: DISPONIVEL, OCUPADA
}

