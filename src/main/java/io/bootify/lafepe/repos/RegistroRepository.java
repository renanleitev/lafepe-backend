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

    List<Registro> findAllByEstoqueId(Long estoqueId);

    List<Registro> findAllByEstoqueLote(String lote);

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

    // Registro entre datas
    @Query("SELECT p FROM Registro p WHERE p.data BETWEEN :dataInicio AND :dataLimite")
    List<Registro> findAllByDataEntreDatas(@Param("dataInicio") LocalDate dataInicio, @Param("dataLimite") LocalDate dataLimite);

    // Registro especifico por lote entre datas
    @Query("SELECT p FROM Registro p WHERE p.estoque.lote = :lote AND p.data BETWEEN :dataInicio AND :dataLimite")
    List<Registro> findRegistroByLoteAndDataEntreDatas(
            @Param("lote") String lote,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataLimite") LocalDate dataLimite
    );
}
