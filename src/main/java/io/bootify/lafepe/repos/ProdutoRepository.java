package io.bootify.lafepe.repos;

import io.bootify.lafepe.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // LIKE = Mais lento (não procura por índice)
    // Pode colocar pageable como segundo argumento, caso queira filtrar por página

    // Nome
    @Query("SELECT p FROM Produto p WHERE p.nome LIKE CONCAT('%',:nome,'%')")
    List<Produto> findAllByNomeLike(@Param("nome") String nome);

    // Codigo
    @Query("SELECT p FROM Produto p WHERE p.codigo LIKE CONCAT('%',:codigo,'%')")
    List<Produto> findAllByCodigoLike(@Param("codigo") String codigo);

    // Fabricante
    @Query("SELECT p FROM Produto p WHERE p.fabricante LIKE CONCAT('%',:fabricante,'%')")
    List<Produto> findAllByFabricanteLike(@Param("fabricante") String fabricante);

    // Preço exato
    List<Produto> findAllByPrecoUnitario(Double precoUnitario);

    // Preço menor
    @Query("SELECT p FROM Produto p WHERE p.precoUnitario < :precoUnitario")
    List<Produto> findAllByPrecoUnitarioLessThan(@Param("precoUnitario") Double precoUnitario);

    // Preço menor ou igual
    @Query("SELECT p FROM Produto p WHERE p.precoUnitario <= :precoUnitario")
    List<Produto> findAllByPrecoUnitarioLessThanOrEqualTo(@Param("precoUnitario") Double precoUnitario);

    // Preço maior
    @Query("SELECT p FROM Produto p WHERE p.precoUnitario > :precoUnitario")
    List<Produto> findAllByPrecoUnitarioGreaterThan(@Param("precoUnitario") Double precoUnitario);

    // Preço maior ou igual
    @Query("SELECT p FROM Produto p WHERE p.precoUnitario >= :precoUnitario")
    List<Produto> findAllByPrecoUnitarioGreaterThanOrEqualTo(@Param("precoUnitario") Double precoUnitario);
}
