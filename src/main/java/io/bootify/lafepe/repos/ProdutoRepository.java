package io.bootify.lafepe.repos;

import io.bootify.lafepe.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
