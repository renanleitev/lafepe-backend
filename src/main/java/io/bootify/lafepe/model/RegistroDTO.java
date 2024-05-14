package io.bootify.lafepe.model;

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

    private Integer saldo;

    @NotNull
    private LocalDate data;

    private Long estoque;

}
