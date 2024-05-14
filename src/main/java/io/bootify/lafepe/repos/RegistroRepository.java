package io.bootify.lafepe.repos;

import io.bootify.lafepe.domain.Estoque;
import io.bootify.lafepe.domain.Produto;
import io.bootify.lafepe.domain.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;


public interface RegistroRepository extends JpaRepository<Registro, Long> {

    Registro findFirstByEstoqueId(Estoque estoque);

    // LIKE = Mais lento (não procura por índice)
    // Pode colocar pageable como segundo argumento, caso queira filtrar por página

    // Entrada
    Registro findAllByEntrada(@Param("entrada") Integer entrada);

    @Query("SELECT p FROM Registro p WHERE p.entrada LIKE CONCAT('%',:entrada,'%')")
    Registro findAllByEntradaLike(@Param("entrada") Integer entrada);

    @Query("SELECT p FROM Registro p WHERE p.entrada < :entrada")
    Registro findAllByEntradaLessThan(@Param("entrada") Integer entrada);

    @Query("SELECT p FROM Registro p WHERE p.entrada <= :entrada")
    Registro findAllByEntradaLessThanOrEqualTo(@Param("entrada") Integer entrada);

    @Query("SELECT p FROM Registro p WHERE p.entrada > :entrada")
    Registro findAllByEntradaGreaterThan(@Param("entrada") Integer entrada);

    @Query("SELECT p FROM Registro p WHERE p.entrada >= :entrada")
    Registro findAllByEntradaGreaterThanOrEqualTo(@Param("entrada") Integer entrada);

    // Saida
    Registro findAllBySaida(@Param("saida") Integer saida);

    @Query("SELECT p FROM Registro p WHERE p.saida LIKE CONCAT('%',:saida,'%')")
    Registro findAllBySaidaLike(@Param("saida") Integer saida);

    @Query("SELECT p FROM Registro p WHERE p.saida < :saida")
    Registro findAllBySaidaLessThan(@Param("saida") Integer saida);

    @Query("SELECT p FROM Registro p WHERE p.saida <= :saida")
    Registro findAllBySaidaLessThanOrEqualTo(@Param("saida") Integer saida);

    @Query("SELECT p FROM Registro p WHERE p.saida > :saida")
    Registro findAllBySaidaGreaterThan(@Param("saida") Integer saida);

    @Query("SELECT p FROM Registro p WHERE p.saida >= :saida")
    Registro findAllBySaidaGreaterThanOrEqualTo(@Param("saida") Integer saida);

    // Saldo
    Registro findAllBySaldo(@Param("saldo") Integer saldo);

    @Query("SELECT p FROM Registro p WHERE p.saldo LIKE CONCAT('%',:saldo,'%')")
    Registro findAllBySaldoLike(@Param("saldo") Integer saldo);

    @Query("SELECT p FROM Registro p WHERE p.saldo < :saldo")
    Registro findAllBySaldoLessThan(@Param("saldo") Integer saldo);

    @Query("SELECT p FROM Registro p WHERE p.saldo <= :saldo")
    Registro findAllBySaldoLessThanOrEqualTo(@Param("saldo") Integer saldo);

    @Query("SELECT p FROM Registro p WHERE p.saldo > :saldo")
    Registro findAllBySaldoGreaterThan(@Param("saldo") Integer saldo);

    @Query("SELECT p FROM Registro p WHERE p.saldo >= :saldo")
    Registro findAllBySaldoGreaterThanOrEqualTo(@Param("saldo") Integer saldo);

    // Data exata
    Estoque findAllByData(LocalDate data);

    // Data menor
    @Query("SELECT p FROM Registro p WHERE p.data < :data")
    Produto findAllByDataLessThan(@Param("validade") LocalDate data);

    // Data menor ou igual
    @Query("SELECT p FROM Registro p WHERE p.data <= :data")
    Produto findAllByDataLessThanOrEqualTo(@Param("validade") LocalDate data);

    // Data maior
    @Query("SELECT p FROM Registro p WHERE p.data > :data")
    Produto findAllByDataGreaterThan(@Param("validade") LocalDate data);

    // Data maior ou igual
    @Query("SELECT p FROM Registro p WHERE p.data >= :data")
    Produto findAllByDataGreaterThanOrEqualTo(@Param("validade") LocalDate data);
}
