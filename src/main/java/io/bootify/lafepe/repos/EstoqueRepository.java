package io.bootify.lafepe.repos;

import io.bootify.lafepe.domain.Estoque;
import io.bootify.lafepe.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    Estoque findFirstByProduto(Produto produto);

}
