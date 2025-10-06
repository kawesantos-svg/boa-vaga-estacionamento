package Inova.Grupo.BoaVaga.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pagamentos")
@Data
public class Pagamento {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private BigDecimal valor; // Usar BigDecimal para valores monetários

    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento; // Enum: CARTAO, DINHEIRO, PIX

    //Relacionamento com Estadia
    @OneToOne
    @JoinColumn(name = "estadia_id", nullable = false)
    @JsonIgnore
    private Estadia estadia;

    public void setEstadia(Estadia estadia) {
        this.estadia = estadia;
        if (estadia != null && estadia.getPagamento() != this) {
            estadia.setPagamento(this);
        }
    }

}


