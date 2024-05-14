package io.bootify.lafepe.service;

import io.bootify.lafepe.domain.Estoque;
import io.bootify.lafepe.domain.Produto;
import io.bootify.lafepe.domain.Registro;
import io.bootify.lafepe.model.EstoqueDTO;
import io.bootify.lafepe.repos.EstoqueRepository;
import io.bootify.lafepe.repos.ProdutoRepository;
import io.bootify.lafepe.repos.RegistroRepository;
import io.bootify.lafepe.util.NotFoundException;
import io.bootify.lafepe.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;
    private final RegistroRepository registroRepository;

    public EstoqueService(final EstoqueRepository estoqueRepository,
            final ProdutoRepository produtoRepository,
            final RegistroRepository registroRepository) {
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
        this.registroRepository = registroRepository;
    }

    public List<EstoqueDTO> findAll() {
        final List<Estoque> estoques = estoqueRepository.findAll(Sort.by("id"));
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public EstoqueDTO get(final Long id) {
        return estoqueRepository.findById(id)
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EstoqueDTO estoqueDTO) {
        final Estoque estoque = new Estoque();
        mapToEntity(estoqueDTO, estoque);
        return estoqueRepository.save(estoque).getId();
    }

    public void update(final Long id, final EstoqueDTO estoqueDTO) {
        final Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(estoqueDTO, estoque);
        estoqueRepository.save(estoque);
    }

    public void delete(final Long id) {
        estoqueRepository.deleteById(id);
    }

    private EstoqueDTO mapToDTO(final Estoque estoque, final EstoqueDTO estoqueDTO) {
        estoqueDTO.setId(estoque.getId());
        estoqueDTO.setLote(estoque.getLote());
        estoqueDTO.setQuantidade(estoque.getQuantidade());
        estoqueDTO.setUnidade(estoque.getUnidade());
        estoqueDTO.setQuarentena(estoque.getQuarentena());
        estoqueDTO.setValidade(estoque.getValidade());
        estoqueDTO.setDescricao(estoque.getDescricao());
        estoqueDTO.setProduto(estoque.getProduto() == null ? null : estoque.getProduto().getId());
        return estoqueDTO;
    }

    private Estoque mapToEntity(final EstoqueDTO estoqueDTO, final Estoque estoque) {
        estoque.setLote(estoqueDTO.getLote());
        estoque.setQuantidade(estoqueDTO.getQuantidade());
        estoque.setUnidade(estoqueDTO.getUnidade());
        estoque.setQuarentena(estoqueDTO.getQuarentena());
        estoque.setValidade(estoqueDTO.getValidade());
        estoque.setDescricao(estoqueDTO.getDescricao());
        final Produto produto = estoqueDTO.getProduto() == null ? null : produtoRepository.findById(estoqueDTO.getProduto())
                .orElseThrow(() -> new NotFoundException("produto not found"));
        estoque.setProduto(produto);
        return estoque;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Registro estoqueIdRegistro = registroRepository.findFirstByEstoqueId(estoque);
        if (estoqueIdRegistro != null) {
            referencedWarning.setKey("estoque.registro.estoqueId.referenced");
            referencedWarning.addParam(estoqueIdRegistro.getId());
            return referencedWarning;
        }
        return null;
    }

}
