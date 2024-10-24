package io.github.joaoguilherme.msclientes.application.dto;

import io.github.joaoguilherme.msclientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteSaveDto {
    private String cpf;
    private String nome;
    private Integer idade;

    public Cliente toModel() {
        return new Cliente(cpf, nome, idade);
    }

}
