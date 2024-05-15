package io.bootify.lafepe.model;

import io.bootify.lafepe.domain.Estoque;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegistroDTO {

    private Long id;

    private Integer entrada;

    private Integer saida;

    @NotNull
    private LocalDate data;

    private Long estoqueId;

    private Estoque estoque;

}
