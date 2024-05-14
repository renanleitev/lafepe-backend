package io.bootify.lafepe.repos;

import io.bootify.lafepe.domain.Estoque;
import io.bootify.lafepe.domain.Registro;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RegistroRepository extends JpaRepository<Registro, Long> {

    Registro findFirstByEstoqueId(Estoque estoque);

}
