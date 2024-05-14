package io.bootify.lafepe.repos;

import io.bootify.lafepe.domain.Estoque;
import io.bootify.lafepe.domain.Produto;
import io.bootify.lafepe.domain.Registro;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    Estoque findFirstByProduto(Produto produto);

    // LIKE = Mais lento (não procura por índice)
    // Pode colocar pageable como segundo argumento, caso queira filtrar por página

    // Lote
    @Query("SELECT p FROM Estoque p WHERE p.lote LIKE CONCAT('%',:lote,'%')")
    List<Estoque> findAllByLoteLike(@Param("lote") String lote);

    // Quantidade
    List<Estoque> findAllByQuantidade(Integer quantidade);

    // Quantidade menor
    @Query("SELECT p FROM Estoque p WHERE p.quantidade < :quantidade")
    List<Estoque> findAllByQuantidadeLessThan(@Param("quantidade") Integer quantidade);

    // Quantidade menor ou igual
    @Query("SELECT p FROM Estoque p WHERE p.quantidade <= :quantidade")
    List<Estoque> findAllByQuantidadeLessThanOrEqualTo(@Param("quantidade") Integer quantidade);

    // Quantidade maior
    @Query("SELECT p FROM Estoque p WHERE p.quantidade > :quantidade")
    List<Estoque> findAllByQuantidadeGreaterThan(@Param("quantidade") Integer quantidade);

    // Quantidade maior ou igual
    @Query("SELECT p FROM Estoque p WHERE p.quantidade >= :quantidade")
    List<Estoque> findAllByQuantidadeGreaterThanOrEqualTo(@Param("quantidade") Integer quantidade);

    // Unidade
    @Query("SELECT p FROM Estoque p WHERE p.unidade LIKE CONCAT('%',:unidade,'%')")
    List<Estoque> findAllByUnidadeLike(@Param("unidade") String unidade);

    // Quarentena
    List<Estoque> findAllByQuarentena(Integer quarentena);

    // Quantidade menor
    @Query("SELECT p FROM Estoque p WHERE p.quarentena < :quarentena")
    List<Estoque> findAllByQuarentenaLessThan(@Param("quarentena") Integer quarentena);

    // Quantidade menor ou igual
    @Query("SELECT p FROM Estoque p WHERE p.quarentena <= :quarentena")
    List<Estoque> findAllByQuarentenaLessThanOrEqualTo(@Param("quarentena") Integer quarentena);

    // Quantidade maior
    @Query("SELECT p FROM Estoque p WHERE p.quarentena > :quarentena")
    List<Estoque> findAllByQuarentenaGreaterThan(@Param("quarentena") Integer quarentena);

    // Quantidade maior ou igual
    @Query("SELECT p FROM Estoque p WHERE p.quarentena >= :quarentena")
    List<Estoque> findAllByQuarentenaGreaterThanOrEqualTo(@Param("quarentena") Integer quarentena);

    // Validade exata
    List<Estoque> findAllByValidade(LocalDate validade);

    // Validade menor
    @Query("SELECT p FROM Estoque p WHERE p.validade < :validade")
    List<Estoque> findAllByValidadeLessThan(@Param("validade") LocalDate validade);

    // Validade menor ou igual
    @Query("SELECT p FROM Estoque p WHERE p.validade <= :validade")
    List<Estoque> findAllByValidadeLessThanOrEqualTo(@Param("validade") LocalDate validade);

    // Validade maior
    @Query("SELECT p FROM Estoque p WHERE p.validade > :validade")
    List<Estoque> findAllByValidadeGreaterThan(@Param("validade") LocalDate validade);

    // Validade maior ou igual
    @Query("SELECT p FROM Estoque p WHERE p.validade >= :validade")
    List<Estoque> findAllByValidadeGreaterThanOrEqualTo(@Param("validade") LocalDate validade);

    // Vencidos
    @Query("SELECT p FROM Estoque p WHERE p.validade < CURRENT_DATE")
    List<Estoque> findAllByValidadeVencidos();

    // Validade por mes
    @Query("SELECT p FROM Estoque p WHERE p.validade BETWEEN CURRENT_DATE AND :dataLimite")
    List<Estoque> findAllByValidadePeriodo(@Param("dataLimite") LocalDate dataLimite);

    // Estoque
    @Query("SELECT p FROM Estoque p WHERE p.descricao LIKE CONCAT('%',:descricao,'%')")
    List<Estoque> findAllByDescricaoLike(@Param("descricao") String descricao);
}
