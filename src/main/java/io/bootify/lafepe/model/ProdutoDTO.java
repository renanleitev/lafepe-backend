package io.bootify.lafepe.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProdutoDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String codigo;

    @NotNull
    @Size(max = 255)
    private String nome;

    @NotNull
    @Size(max = 500)
    private String descricao;

    @NotNull
    private Double precoUnitario;

}
