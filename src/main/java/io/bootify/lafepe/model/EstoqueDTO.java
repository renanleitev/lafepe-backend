package io.bootify.lafepe.model;

import io.bootify.lafepe.domain.Produto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EstoqueDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String lote;

    @NotNull
    private Integer quantidade;

    @NotNull
    @Size(max = 255)
    private String unidade;

    @NotNull
    private Integer quarentena;

    @NotNull
    private Integer saldoOriginal;

    @NotNull
    private Integer saldoAtual;

    @NotNull
    private LocalDate validade;

    private Long produtoId;

    private Produto produto;

}
