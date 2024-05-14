package io.bootify.lafepe.repos;

import io.bootify.lafepe.domain.Estoque;
import io.bootify.lafepe.domain.Produto;
import io.bootify.lafepe.domain.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface RegistroRepository extends JpaRepository<Registro, Long> {

    Registro findFirstByEstoqueId(Estoque estoque);

    // LIKE = Mais lento (não procura por índice)
    // Pode colocar pageable como segundo argumento, caso queira filtrar por página

    // Entrada
    List<Registro> findAllByEntrada(@Param("entrada") Integer entrada);

    // Entrada menor
    @Query("SELECT p FROM Registro p WHERE p.entrada < :entrada")
    List<Registro> findAllByEntradaLessThan(@Param("entrada") Integer entrada);

    // Entrada menor ou igual
    @Query("SELECT p FROM Registro p WHERE p.entrada <= :entrada")
    List<Registro> findAllByEntradaLessThanOrEqualTo(@Param("entrada") Integer entrada);

    // Entrada maior
    @Query("SELECT p FROM Registro p WHERE p.entrada > :entrada")
    List<Registro> findAllByEntradaGreaterThan(@Param("entrada") Integer entrada);

    // Entrada maior ou igual
    @Query("SELECT p FROM Registro p WHERE p.entrada >= :entrada")
    List<Registro> findAllByEntradaGreaterThanOrEqualTo(@Param("entrada") Integer entrada);

    // Saida
    List<Registro> findAllBySaida(@Param("saida") Integer saida);

    // Saida menor
    @Query("SELECT p FROM Registro p WHERE p.saida < :saida")
    List<Registro> findAllBySaidaLessThan(@Param("saida") Integer saida);

    // Saida menor ou igual
    @Query("SELECT p FROM Registro p WHERE p.saida <= :saida")
    List<Registro> findAllBySaidaLessThanOrEqualTo(@Param("saida") Integer saida);

    // Saida maior
    @Query("SELECT p FROM Registro p WHERE p.saida > :saida")
    List<Registro> findAllBySaidaGreaterThan(@Param("saida") Integer saida);

    // Saida maior ou igual
    @Query("SELECT p FROM Registro p WHERE p.saida >= :saida")
    List<Registro> findAllBySaidaGreaterThanOrEqualTo(@Param("saida") Integer saida);

    // Saldo
    List<Registro> findAllBySaldo(@Param("saldo") Integer saldo);

    // Saldo menor
    @Query("SELECT p FROM Registro p WHERE p.saldo < :saldo")
    List<Registro> findAllBySaldoLessThan(@Param("saldo") Integer saldo);

    // Saldo menor ou igual
    @Query("SELECT p FROM Registro p WHERE p.saldo <= :saldo")
    List<Registro> findAllBySaldoLessThanOrEqualTo(@Param("saldo") Integer saldo);

    // Saldo maior
    @Query("SELECT p FROM Registro p WHERE p.saldo > :saldo")
    List<Registro> findAllBySaldoGreaterThan(@Param("saldo") Integer saldo);

    // Saldo maior ou igual
    @Query("SELECT p FROM Registro p WHERE p.saldo >= :saldo")
    List<Registro> findAllBySaldoGreaterThanOrEqualTo(@Param("saldo") Integer saldo);

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
