package Inova.Grupo.BoaVaga.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "estadias")
@Data
public class Estadia {
    @Id
    @GeneratedValue
    private UUID id;

    // Relacionamento com Vaga
    @ManyToOne
    @JoinColumn(name = "vaga_id", nullable = false)
    private Vaga vaga;

    // Relacionamento com Carro
    @ManyToOne
    @JoinColumn(name = "carro_placa", nullable = false)
    private Carro carro;

    @Column(nullable = false)
    private LocalDateTime horarioEntrada;
    private LocalDateTime horarioSaida;
    private boolean ativa = true;

    // Relacionamento com o pagamento
    @OneToOne(mappedBy = "estadia", cascade = CascadeType.ALL)
    private Pagamento pagamento;
}
