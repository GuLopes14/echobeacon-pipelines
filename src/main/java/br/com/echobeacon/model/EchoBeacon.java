package br.com.echobeacon.model;

import br.com.echobeacon.model.enums.StatusConexao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class EchoBeacon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo 'número de identificação' é obrigatório")
    @Min(value = 1, message = "O campo 'número de identificação' deve ser um número inteiro positivo")
    private int numeroIdentificacao;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O campo 'statusConexao' é obrigatório")
    private StatusConexao statusConexao;
}