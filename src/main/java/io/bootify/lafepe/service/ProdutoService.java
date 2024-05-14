package io.bootify.lafepe.service;

import io.bootify.lafepe.domain.Estoque;
import io.bootify.lafepe.domain.Produto;
import io.bootify.lafepe.model.ProdutoDTO;
import io.bootify.lafepe.repos.EstoqueRepository;
import io.bootify.lafepe.repos.ProdutoRepository;
import io.bootify.lafepe.util.NotFoundException;
import io.bootify.lafepe.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;

    public ProdutoService(final ProdutoRepository produtoRepository,
            final EstoqueRepository estoqueRepository) {
        this.produtoRepository = produtoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    public List<ProdutoDTO> findAll() {
        final List<Produto> produtos = produtoRepository.findAll(Sort.by("id"));
        return produtos.stream()
                .map(produto -> mapToDTO(produto, new ProdutoDTO()))
                .toList();
    }

    public ProdutoDTO get(final Long id) {
        return produtoRepository.findById(id)
                .map(produto -> mapToDTO(produto, new ProdutoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public List<ProdutoDTO> getProdutoByNomeLike(final String nome) {
        final List<Produto> produtos = produtoRepository.findAllByNomeLike(nome);
        return produtos.stream()
                .map(produto -> mapToDTO(produto, new ProdutoDTO()))
                .toList();
    }

    public List<ProdutoDTO> getProdutoByCodigoLike(final String codigo) {
        final List<Produto> produtos = produtoRepository.findAllByCodigoLike(codigo);
        return produtos.stream()
                .map(produto -> mapToDTO(produto, new ProdutoDTO()))
                .toList();
    }

    public List<ProdutoDTO> getProdutoByFabricanteLike(final String fabricante) {
        final List<Produto> produtos = produtoRepository.findAllByFabricanteLike(fabricante);
        return produtos.stream()
                .map(produto -> mapToDTO(produto, new ProdutoDTO()))
                .toList();
    }

    public List<ProdutoDTO> getProdutoByPrecoUnitario(final Double precoUnitario) {
        final List<Produto> produtos = produtoRepository.findAllByPrecoUnitario(precoUnitario);
        return produtos.stream()
                .map(produto -> mapToDTO(produto, new ProdutoDTO()))
                .toList();
    }

    public List<ProdutoDTO> getProdutoByPrecoUnitarioLessThan(final Double precoUnitario) {
        final List<Produto> produtos = produtoRepository.findAllByPrecoUnitarioLessThan(precoUnitario);
        return produtos.stream()
                .map(produto -> mapToDTO(produto, new ProdutoDTO()))
                .toList();
    }

    public List<ProdutoDTO> getProdutoByPrecoUnitarioLessThanOrEqualTo(final Double precoUnitario) {
        final List<Produto> produtos = produtoRepository.findAllByPrecoUnitarioLessThanOrEqualTo(precoUnitario);
        return produtos.stream()
                .map(produto -> mapToDTO(produto, new ProdutoDTO()))
                .toList();
    }

    public List<ProdutoDTO> getProdutoByPrecoUnitarioGreaterThan(final Double precoUnitario) {
        final List<Produto> produtos = produtoRepository.findAllByPrecoUnitarioGreaterThan(precoUnitario);
        return produtos.stream()
                .map(produto -> mapToDTO(produto, new ProdutoDTO()))
                .toList();
    }

    public List<ProdutoDTO> getProdutoByPrecoUnitarioGreaterThanOrEqualTo(final Double precoUnitario) {
        final List<Produto> produtos = produtoRepository.findAllByPrecoUnitarioGreaterThanOrEqualTo(precoUnitario);
        return produtos.stream()
                .map(produto -> mapToDTO(produto, new ProdutoDTO()))
                .toList();
    }

    public Long create(final ProdutoDTO produtoDTO) {
        final Produto produto = new Produto();
        mapToEntity(produtoDTO, produto);
        return produtoRepository.save(produto).getId();
    }

    public void update(final Long id, final ProdutoDTO produtoDTO) {
        final Produto produto = produtoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(produtoDTO, produto);
        produtoRepository.save(produto);
    }

    public void delete(final Long id) {
        produtoRepository.deleteById(id);
    }

    private ProdutoDTO mapToDTO(final Produto produto, final ProdutoDTO produtoDTO) {
        produtoDTO.setId(produto.getId());
        produtoDTO.setCodigo(produto.getCodigo());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setFabricante(produto.getFabricante());
        produtoDTO.setPrecoUnitario(produto.getPrecoUnitario());
        return produtoDTO;
    }

    private Produto mapToEntity(final ProdutoDTO produtoDTO, final Produto produto) {
        produto.setCodigo(produtoDTO.getCodigo());
        produto.setNome(produtoDTO.getNome());
        produto.setFabricante(produtoDTO.getFabricante());
        produto.setPrecoUnitario(produtoDTO.getPrecoUnitario());
        return produto;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Produto produto = produtoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Estoque produtoEstoque = estoqueRepository.findFirstByProduto(produto);
        if (produtoEstoque != null) {
            referencedWarning.setKey("produto.estoque.produto.referenced");
            referencedWarning.addParam(produtoEstoque.getId());
            return referencedWarning;
        }
        return null;
    }

}
