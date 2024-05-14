package io.bootify.lafepe.repos;

import io.bootify.lafepe.domain.Estoque;
import io.bootify.lafepe.domain.Produto;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;


public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    Estoque findFirstByProduto(Produto produto);

    // LIKE = Mais lento (não procura por índice)
    // Pode colocar pageable como segundo argumento, caso queira filtrar por página

    // Lote
    @Query("SELECT p FROM Estoque p WHERE p.lote LIKE CONCAT('%',:lote,'%')")
    Estoque findAllByLoteLike(@Param("lote") String lote);

    // Quantidade
    @Query("SELECT p FROM Estoque p WHERE p.quantidade LIKE CONCAT('%',:quantidade,'%')")
    Estoque findAllByQuantidadeLike(@Param("quantidade") Integer quantidade);

    // Unidade
    @Query("SELECT p FROM Estoque p WHERE p.unidade LIKE CONCAT('%',:unidade,'%')")
    Estoque findAllByUnidadeLike(@Param("unidade") String unidade);

    // Quarentena
    @Query("SELECT p FROM Estoque p WHERE p.quarentena LIKE CONCAT('%',:quarentena,'%')")
    Estoque findAllByQuarentenaLike(@Param("quarentena") Integer quarentena);

    // Validade exata
    Estoque findAllByValidade(LocalDate validade);

    // Validade menor
    @Query("SELECT p FROM Estoque p WHERE p.validade < :validade")
    Produto findAllByValidadeLessThan(@Param("validade") LocalDate validade);

    // Validade menor ou igual
    @Query("SELECT p FROM Estoque p WHERE p.validade <= :validade")
    Produto findAllByValidadeLessThanOrEqualTo(@Param("validade") LocalDate validade);

    // Validade maior
    @Query("SELECT p FROM Estoque p WHERE p.validade > :validade")
    Produto findAllByValidadeGreaterThan(@Param("validade") LocalDate validade);

    // Validade maior ou igual
    @Query("SELECT p FROM Estoque p WHERE p.validade >= :validade")
    Produto findAllByValidadeGreaterThanOrEqualTo(@Param("validade") LocalDate validade);

    // Estoque
    @Query("SELECT p FROM Estoque p WHERE p.descricao LIKE CONCAT('%',:descricao,'%')")
    Estoque findAllByDescricaoLike(@Param("descricao") String descricao);
}
