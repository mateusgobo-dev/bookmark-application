package br.com.mgobo.profileapp.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CheckUser(
                        @NotNull(message = "Informe o email")
                        @NotBlank(message = "Email nao pode ser vazio")
                        String email,

                        @NotNull(message = "Informe a senha")
                        @NotBlank(message = "Senha nao pode ser vazia")
                        String password) {
}
