// src/main/java/Inova/Grupo/BoaVaga/dto/VagaDTO.java
package Inova.Grupo.BoaVaga.dto;

import jakarta.validation.constraints.NotBlank;

public class VagaDTO {

    @NotBlank(message = "O número da vaga não pode estar em branco.")
    private String numeroVaga;

    // Getter e Setter
    public String getNumeroVaga() {
        return numeroVaga;
    }

    public void setNumeroVaga(String numeroVaga) {
        this.numeroVaga = numeroVaga;
    }
}