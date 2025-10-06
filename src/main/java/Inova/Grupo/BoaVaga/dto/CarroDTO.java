// src/main/java/Inova/Grupo/BoaVaga/dto/CarroDTO.java
package Inova.Grupo.BoaVaga.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;

public class CarroDTO {
    @NotBlank(message = "A placa é obrigatória")
    //@Pattern(regexp = "[A-Z]{3}[0-9][A-Z0-9][0-9]{2}", message = "Formato de placa inválido.")
    private String placa;

    @NotBlank(message = "O modelo é obrigatório")
    private String modelo;

    private String cor;

    @NotNull(message = "O ID do proprietário é obrigatório")
    private UUID proprietarioId;

    // Getters e Setters
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    public UUID getProprietarioId() { return proprietarioId; }
    public void setProprietarioId(UUID proprietarioId) { this.proprietarioId = proprietarioId; }
}