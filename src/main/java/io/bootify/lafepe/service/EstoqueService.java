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

import java.time.LocalDate;
import java.util.List;

import org.springframework.cglib.core.Local;
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

    public List<EstoqueDTO> getEstoqueByLoteLike(final String lote) {
        final List<Estoque> estoques = estoqueRepository.findAllByLoteLike(lote);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByQuantidade(final Integer quantidade) {
        final List<Estoque> estoques = estoqueRepository.findAllByQuantidade(quantidade);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByQuantidadeLessThan(final Integer quantidade) {
        final List<Estoque> estoques = estoqueRepository.findAllByQuantidadeLessThan(quantidade);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByQuantidadeLessThanOrEqualTo(final Integer quantidade) {
        final List<Estoque> estoques= estoqueRepository.findAllByQuantidadeLessThanOrEqualTo(quantidade);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByQuantidadeGreaterThan(final Integer quantidade) {
        final List<Estoque> estoques = estoqueRepository.findAllByQuantidadeGreaterThan(quantidade);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByQuantidadeGreaterThanOrEqualTo(final Integer quantidade) {
        final List<Estoque> estoques = estoqueRepository.findAllByQuantidadeGreaterThanOrEqualTo(quantidade);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByUnidadeLike(final String unidade) {
        final List<Estoque> estoques= estoqueRepository.findAllByUnidadeLike(unidade);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByValidade(final LocalDate validade) {
        final List<Estoque> estoques = estoqueRepository.findAllByValidade(validade);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByValidadeVencidos() {
        final List<Estoque> estoques = estoqueRepository.findAllByValidadeVencidos();
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByValidadePeriodo(final Integer periodo) {
        LocalDate dataLimite = LocalDate.now().plusMonths(periodo);
        final List<Estoque> estoques = estoqueRepository.findAllByValidadePeriodo(dataLimite);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByValidadeLessThan(final LocalDate validade) {
        final List<Estoque> estoques = estoqueRepository.findAllByValidadeLessThan(validade);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByValidadeLessThanOrEqualTo(final LocalDate validade) {
        final List<Estoque> estoques = estoqueRepository.findAllByValidadeLessThanOrEqualTo(validade);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByValidadeGreaterThan(final LocalDate validade) {
        final List<Estoque> estoques = estoqueRepository.findAllByValidadeGreaterThan(validade);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByValidadeGreaterThanOrEqualTo(final LocalDate validade) {
        final List<Estoque> estoques = estoqueRepository.findAllByValidadeGreaterThanOrEqualTo(validade);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueBySaldoOriginal(final Integer saldo) {
        final List<Estoque> estoques = estoqueRepository.findAllBySaldoOriginal(saldo);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueBySaldoOriginalLessThan(final Integer saldo) {
        final List<Estoque> estoques = estoqueRepository.findAllBySaldoOriginalLessThan(saldo);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueBySaldoOriginalLessThanOrEqualTo(final Integer saldo) {
        final List<Estoque> estoques = estoqueRepository.findAllBySaldoOriginalLessThanOrEqualTo(saldo);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueBySaldoOriginalGreaterThan(final Integer saldo) {
        final List<Estoque> estoques = estoqueRepository.findAllBySaldoOriginalGreaterThan(saldo);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueBySaldoOriginalGreaterThanOrEqualTo(final Integer saldo) {
        final List<Estoque> estoques = estoqueRepository.findAllBySaldoOriginalGreaterThanOrEqualTo(saldo);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueBySaldoAtual(final Integer saldo) {
        final List<Estoque> estoques = estoqueRepository.findAllBySaldoAtual(saldo);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueBySaldoAtualLessThan(final Integer saldo) {
        final List<Estoque> estoques = estoqueRepository.findAllBySaldoAtualLessThan(saldo);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueBySaldoAtualLessThanOrEqualTo(final Integer saldo) {
        final List<Estoque> estoques = estoqueRepository.findAllBySaldoAtualLessThanOrEqualTo(saldo);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueBySaldoAtualGreaterThan(final Integer saldo) {
        final List<Estoque> estoques = estoqueRepository.findAllBySaldoAtualGreaterThan(saldo);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueBySaldoAtualGreaterThanOrEqualTo(final Integer saldo) {
        final List<Estoque> estoques = estoqueRepository.findAllBySaldoAtualGreaterThanOrEqualTo(saldo);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByQuarentena(final Integer quarentena) {
        final List<Estoque> estoques = estoqueRepository.findAllByQuarentena(quarentena);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByQuarentenaLessThan(final Integer quarentena) {
        final List<Estoque> estoques = estoqueRepository.findAllByQuarentenaLessThan(quarentena);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByQuarentenaLessThanOrEqualTo(final Integer quarentena) {
        final List<Estoque> estoques = estoqueRepository.findAllByQuarentenaLessThanOrEqualTo(quarentena);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByQuarentenaGreaterThan(final Integer quarentena) {
        final List<Estoque> estoques = estoqueRepository.findAllByQuarentenaGreaterThan(quarentena);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByQuarentenaGreaterThanOrEqualTo(final Integer quarentena) {
        final List<Estoque> estoques = estoqueRepository.findAllByQuarentenaGreaterThanOrEqualTo(quarentena);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByDescricaoLike(final String descricao) {
        final List<Estoque> estoques = estoqueRepository.findAllByDescricaoLike(descricao);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public List<EstoqueDTO> getEstoqueByProdutoId(final Long produtoId) {
        final List<Estoque> estoques = estoqueRepository.findAllByProdutoId(produtoId);
        return estoques.stream()
                .map(estoque -> mapToDTO(estoque, new EstoqueDTO()))
                .toList();
    }

    public Double getEstoquePrejuizoSaldoAtual() {
        final List<Estoque> estoquesVencidos = estoqueRepository.findAllByValidadeVencidos();
        Double prejuizoFinal = 0.0;
        for (Estoque estoque : estoquesVencidos) {
            Double prejuizo = estoque.getSaldoAtual() * estoque.getProduto().getPrecoUnitario();
            prejuizoFinal += prejuizo;
        }
        return prejuizoFinal;
    }

    public Double getEstoquePrejuizoSaldoOriginal() {
        final List<Estoque> estoquesVencidos = estoqueRepository.findAllByValidadeVencidos();
        Double prejuizoFinal = 0.0;
        for (Estoque estoque : estoquesVencidos) {
            Double prejuizo = estoque.getSaldoOriginal() * estoque.getProduto().getPrecoUnitario();
            prejuizoFinal += prejuizo;
        }
        return prejuizoFinal;
    }

    public Double getEstoquePrejuizoValidadeEntreDatas(LocalDate dataInicio, LocalDate dataLimite) {
        final List<Estoque> estoquesVencidos = estoqueRepository.findAllByValidadeEntreDatas(dataInicio, dataLimite);
        Double prejuizoFinal = 0.0;
        for (Estoque estoque : estoquesVencidos) {
            Double prejuizo = estoque.getSaldoAtual() * estoque.getProduto().getPrecoUnitario();
            prejuizoFinal += prejuizo;
        }
        return prejuizoFinal;
    }

    public Integer getEstoqueQuantidadeValidadeEntreDatas(LocalDate dataInicio, LocalDate dataLimite) {
        final List<Estoque> estoquesVencidos = estoqueRepository.findAllByValidadeEntreDatas(dataInicio, dataLimite);
        return estoquesVencidos.size();
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
        estoqueDTO.setSaldoAtual(estoque.getSaldoAtual());
        estoqueDTO.setSaldoOriginal(estoque.getSaldoOriginal());
        estoqueDTO.setValidade(estoque.getValidade());
        estoqueDTO.setDescricao(estoque.getDescricao());
        estoqueDTO.setProdutoId(estoque.getProduto() == null ? null : estoque.getProduto().getId());
        estoqueDTO.setProduto(estoque.getProduto());
        return estoqueDTO;
    }

    private Estoque mapToEntity(final EstoqueDTO estoqueDTO, final Estoque estoque) {
        estoque.setLote(estoqueDTO.getLote());
        estoque.setQuantidade(estoqueDTO.getQuantidade());
        estoque.setUnidade(estoqueDTO.getUnidade());
        estoque.setQuarentena(estoqueDTO.getQuarentena());
        estoque.setSaldoAtual(estoqueDTO.getSaldoAtual());
        estoque.setSaldoOriginal(estoqueDTO.getSaldoOriginal());
        estoque.setValidade(estoqueDTO.getValidade());
        estoque.setDescricao(estoqueDTO.getDescricao());
        final Produto produto = estoqueDTO.getProduto() == null ? null : produtoRepository.findById(estoqueDTO.getProdutoId())
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
