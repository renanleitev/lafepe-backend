package io.bootify.lafepe.service;

import io.bootify.lafepe.domain.Estoque;
import io.bootify.lafepe.domain.Registro;
import io.bootify.lafepe.model.RegistroDTO;
import io.bootify.lafepe.repos.EstoqueRepository;
import io.bootify.lafepe.repos.RegistroRepository;
import io.bootify.lafepe.util.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RegistroService {

    private final RegistroRepository registroRepository;
    private final EstoqueRepository estoqueRepository;

    public RegistroService(final RegistroRepository registroRepository,
            final EstoqueRepository estoqueRepository) {
        this.registroRepository = registroRepository;
        this.estoqueRepository = estoqueRepository;
    }

    public List<RegistroDTO> findAll() {
        final List<Registro> registros = registroRepository.findAll(Sort.by("id"));
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public RegistroDTO get(final Long id) {
        return registroRepository.findById(id)
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public List<RegistroDTO> getRegistroByData(LocalDate data) {
        final List<Registro> registros = registroRepository.findAllByData(data);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroByDataLessThan(LocalDate data) {
        final List<Registro> registros = registroRepository.findAllByDataLessThan(data);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroByDataLessThanOrEqualTo(LocalDate data) {
        final List<Registro> registros = registroRepository.findAllByDataLessThanOrEqualTo(data);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroByDataGreaterThan(LocalDate data) {
        final List<Registro> registros = registroRepository.findAllByDataGreaterThan(data);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroByDataGreaterThanOrEqualTo(LocalDate data) {
        final List<Registro> registros = registroRepository.findAllByDataGreaterThanOrEqualTo(data);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroByEstoqueId(Long estoqueId) {
        final List<Registro> registros = registroRepository.findAllByEstoqueId(estoqueId);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroByEstoqueLote(String lote) {
        final List<Registro> registros = registroRepository.findAllByEstoqueLote(lote);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public Integer getRegistroEntrada(LocalDate dataInicio, LocalDate dataLimite) {
        final List<Registro> registrosList = registroRepository.findAllByDataEntreDatas(dataInicio, dataLimite);
        Integer totalEntrada = 0;
        for (Registro registro : registrosList) {
            Integer entrada = registro.getEntrada();
            totalEntrada += entrada;
        }
        return totalEntrada;
    }

    public Integer getRegistroSaida(LocalDate dataInicio, LocalDate dataLimite) {
        final List<Registro> registrosList = registroRepository.findAllByDataEntreDatas(dataInicio, dataLimite);
        Integer totalSaida = 0;
        for (Registro registro : registrosList) {
            Integer saida= registro.getSaida();
            totalSaida += saida;
        }
        return totalSaida;
    }

    public Integer getRegistroByLoteEntradaQuantidadeEntreDatas(String lote, LocalDate dataInicio, LocalDate dataLimite) {
        final List<Registro> registrosList = registroRepository
                .findRegistroByLoteAndDataEntreDatas(lote, dataInicio, dataLimite);
        Integer totalEntrada = 0;
        for (Registro registro : registrosList) {
            Integer entrada = registro.getEntrada();
            totalEntrada += entrada;
        }
        return totalEntrada;
    }

    public Integer getRegistroByLoteSaidaQuantidadeEntreDatas(String lote, LocalDate dataInicio, LocalDate dataLimite) {
        final List<Registro> registrosList = registroRepository
                .findRegistroByLoteAndDataEntreDatas(lote, dataInicio, dataLimite);
        Integer totalSaida = 0;
        for (Registro registro : registrosList) {
            Integer saida = registro.getSaida();
            totalSaida += saida;
        }
        return totalSaida;
    }

    public Long create(final RegistroDTO registroDTO) {
        final Registro registro = new Registro();
        mapToEntity(registroDTO, registro);
        // Atualiza o saldo após registro
        Estoque estoque = registro.getEstoque();
        Integer quantidadeInicial = estoque.getQuantidade();
        Integer quantidadeFinal = (quantidadeInicial + registro.getEntrada()) - registro.getSaida();
        estoque.setQuantidade(quantidadeFinal);
        estoque.setSaldoAtual(estoque.getQuantidade() + estoque.getQuarentena());
        estoqueRepository.save(estoque);
        registro.setSaldoInicial(quantidadeInicial);
        registro.setSaldoFinal(quantidadeFinal);
        return registroRepository.save(registro).getId();
    }

    public void update(final Long id, final RegistroDTO registroDTO) {
        final Registro registro = registroRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // Adicionar/remover do saldo apenas o que foi adicionado/removido em excesso
        Integer entrada = registroDTO.getEntrada() - registro.getEntrada();
        Integer saida = registroDTO.getSaida() - registro.getSaida();
        mapToEntity(registroDTO, registro);
        // Atualiza o saldo após registro
        Estoque estoque = registro.getEstoque();
        Integer quantidadeInicial = estoque.getQuantidade();
        Integer quantidadeFinal = (quantidadeInicial + entrada) - saida;
        estoque.setQuantidade(quantidadeFinal);
        estoque.setSaldoAtual(estoque.getQuantidade() + estoque.getQuarentena());
        estoqueRepository.save(estoque);
        registroRepository.save(registro);
    }

    public void delete(final Long id) {
        registroRepository.deleteById(id);
    }

    private RegistroDTO mapToDTO(final Registro registro, final RegistroDTO registroDTO) {
        registroDTO.setId(registro.getId());
        registroDTO.setEntrada(registro.getEntrada());
        registroDTO.setSaida(registro.getSaida());
        registroDTO.setSaldoInicial(registro.getSaldoInicial());
        registroDTO.setSaldoFinal(registro.getSaldoFinal());
        registroDTO.setData(registro.getData());
        registroDTO.setEstoqueId(registro.getEstoque() == null ? null : registro.getEstoque().getId());
        registroDTO.setEstoque(registro.getEstoque());
        return registroDTO;
    }

    private Registro mapToEntity(final RegistroDTO registroDTO, final Registro registro) {
        registro.setId(registroDTO.getId());
        registro.setEntrada(registroDTO.getEntrada());
        registro.setSaida(registroDTO.getSaida());
        registro.setSaldoInicial(registroDTO.getSaldoInicial());
        registro.setSaldoFinal(registroDTO.getSaldoFinal());
        registro.setData(registroDTO.getData());
        final Estoque estoque = registroDTO.getEstoque() == null ? null : estoqueRepository.findById(registroDTO.getEstoqueId())
                .orElseThrow(() -> new NotFoundException("estoqueId not found"));
        registro.setEstoque(estoque);
        return registro;
    }

}
