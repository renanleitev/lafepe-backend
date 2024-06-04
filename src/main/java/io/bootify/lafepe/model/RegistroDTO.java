package io.bootify.lafepe.model;

import io.bootify.lafepe.domain.Estoque;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegistroDTO {

    private Long id;

    @NotNull
    private Integer entrada;

    @NotNull
    private Integer saida;

    private Integer saldoInicial;

    private Integer saldoFinal;

    @NotNull
    private LocalDate data;

    private Long estoqueId;

    private Estoque estoque;

}
