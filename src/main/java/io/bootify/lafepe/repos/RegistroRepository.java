package io.bootify.lafepe.repos;

import io.bootify.lafepe.domain.Estoque;
import io.bootify.lafepe.domain.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface RegistroRepository extends JpaRepository<Registro, Long> {

    Registro findFirstByEstoqueId(Estoque estoque);

    // Data
    List<Registro> findAllByData(LocalDate data);

    // Data menor
    @Query("SELECT p FROM Registro p WHERE p.data < :data")
    List<Registro> findAllByDataLessThan(@Param("data") LocalDate data);

    // Data menor ou igual
    @Query("SELECT p FROM Registro p WHERE p.data <= :data")
    List<Registro> findAllByDataLessThanOrEqualTo(@Param("data") LocalDate data);

    // Data maior
    @Query("SELECT p FROM Registro p WHERE p.data > :data")
    List<Registro> findAllByDataGreaterThan(@Param("data") LocalDate data);

    // Data maior ou igual
    @Query("SELECT p FROM Registro p WHERE p.data >= :data")
    List<Registro> findAllByDataGreaterThanOrEqualTo(@Param("data") LocalDate data);
}
