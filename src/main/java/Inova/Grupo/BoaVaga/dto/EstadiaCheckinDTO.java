package Inova.Grupo.BoaVaga.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EstadiaCheckinDTO {

    @NotBlank(message = "A placa não pode estar em branco.")
    @Pattern(regexp = "[A-Z]{3}[0-9][A-Z0-9][0-9]{2}", message = "Formato de placa inválido.")
    private String placa;

    @NotBlank(message = "O modelo não pode estar em branco.")
    @Size(min = 2, max = 50, message = "O modelo deve ter entre 2 e 50 caracteres.")
    private String modelo;

    @NotBlank(message = "O número da vaga não pode estar em branco.")
    private String numeroVaga;
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroVaga() {
        return numeroVaga;
    }

    public void setNumeroVaga(String numeroVaga) {
        this.numeroVaga = numeroVaga;
    }
}