package br.com.mgobo.profileapp.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CustomerDto(Long id,
                          @NotNull(message = "Nome nao pode ser vazio")
                          @NotBlank(message = "Nome sem preenchimento")
                          String name,
                          String address,

                          @NotNull(message = "Email nao pode ser vazio")
                          @NotBlank(message = "Email sem preenchimento")
                          String mail,

                          @NotNull(message = "Senha nao pode ser vazia")
                          @NotBlank(message = "Senha sem preenchimento")
                          String password) {
    public CustomerDto{
        if(id == null)id = null;
    }
}
