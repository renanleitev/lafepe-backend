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
    private Integer entradaQuarentena;

    @NotNull
    private Integer saidaQuarentena;

    @NotNull
    private Integer entradaQuantidade;

    @NotNull
    private Integer saidaQuantidade;

    private Integer saldoQuarentenaInicial;

    private Integer saldoQuarentenaFinal;

    private Integer saldoQuantidadeInicial;

    private Integer saldoQuantidadeFinal;

    @NotNull
    private LocalDate data;

    private Long estoqueId;

    private Estoque estoque;

}
