package br.com.mgobo.profileapp.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="customer")
@SequenceGenerator(name = "customer_seq", sequenceName = "customer_seq", allocationSize = 1, initialValue = 1)
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    private Long id;

    @NotNull(message = "Nome é obrigatório!")
    @NotBlank(message = "Nome não pode ser deixado em branco!")
    @Column(nullable = false)
    private String name;
    private String address;

    @Email(message = "O email já foi utilizado em outra conta")
    @Column(unique = true)
    private String mail;

    @NotNull(message = "Senha é obrigatória!")
    @NotBlank(message = "Senha não pode ser deixada em branco!")
    @Column(nullable = false)
    private String password;
}
