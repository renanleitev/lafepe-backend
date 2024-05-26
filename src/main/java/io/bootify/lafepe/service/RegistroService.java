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

    public Integer getRegistroEntradaQuarentena(LocalDate dataInicio, LocalDate dataLimite) {
        final List<Registro> registrosList = registroRepository.findAllByDataEntreDatas(dataInicio, dataLimite);
        Integer totalEntradaQuarentena = 0;
        for (Registro registro : registrosList) {
            Integer entradaQuarentena = registro.getEntradaQuarentena();
            totalEntradaQuarentena += entradaQuarentena;
        }
        return totalEntradaQuarentena;
    }

    public Integer getRegistroSaidaQuarentena(LocalDate dataInicio, LocalDate dataLimite) {
        final List<Registro> registrosList = registroRepository.findAllByDataEntreDatas(dataInicio, dataLimite);
        Integer totalSaidaQuarentena = 0;
        for (Registro registro : registrosList) {
            Integer saidaQuarentena = registro.getSaidaQuarentena();
            totalSaidaQuarentena += saidaQuarentena;
        }
        return totalSaidaQuarentena;
    }

    public Integer getRegistroEntradaQuantidade(LocalDate dataInicio, LocalDate dataLimite) {
        final List<Registro> registrosList = registroRepository.findAllByDataEntreDatas(dataInicio, dataLimite);
        Integer totalEntradaQuantidade = 0;
        for (Registro registro : registrosList) {
            Integer entradaQuantidade = registro.getEntradaQuantidade();
            totalEntradaQuantidade += entradaQuantidade;
        }
        return totalEntradaQuantidade;
    }

    public Integer getRegistroSaidaQuantidade(LocalDate dataInicio, LocalDate dataLimite) {
        final List<Registro> registrosList = registroRepository.findAllByDataEntreDatas(dataInicio, dataLimite);
        Integer totalSaidaQuantidade = 0;
        for (Registro registro : registrosList) {
            Integer saidaQuantidade = registro.getSaidaQuantidade();
            totalSaidaQuantidade += saidaQuantidade;
        }
        return totalSaidaQuantidade;
    }

    public Integer getRegistroByLoteEntradaQuantidadeEntreDatas(String lote, LocalDate dataInicio, LocalDate dataLimite) {
        final List<Registro> registrosList = registroRepository
                .findRegistroByLoteAndDataEntreDatas(lote, dataInicio, dataLimite);
        Integer totalEntradaQuantidade = 0;
        for (Registro registro : registrosList) {
            Integer entradaQuantidade = registro.getEntradaQuantidade();
            totalEntradaQuantidade += entradaQuantidade;
        }
        return totalEntradaQuantidade;
    }

    public Integer getRegistroByLoteSaidaQuantidadeEntreDatas(String lote, LocalDate dataInicio, LocalDate dataLimite) {
        final List<Registro> registrosList = registroRepository
                .findRegistroByLoteAndDataEntreDatas(lote, dataInicio, dataLimite);
        Integer totalSaidaQuantidade = 0;
        for (Registro registro : registrosList) {
            Integer saidaQuantidade = registro.getSaidaQuantidade();
            totalSaidaQuantidade += saidaQuantidade;
        }
        return totalSaidaQuantidade;
    }


    public Integer getRegistroByLoteEntradaQuarentenaEntreDatas(String lote, LocalDate dataInicio, LocalDate dataLimite) {
        final List<Registro> registrosList = registroRepository
                .findRegistroByLoteAndDataEntreDatas(lote, dataInicio, dataLimite);
        Integer totalEntradaQuarentena = 0;
        for (Registro registro : registrosList) {
            Integer entradaQuarentena = registro.getEntradaQuarentena();
            totalEntradaQuarentena += entradaQuarentena;
        }
        return totalEntradaQuarentena;
    }

    public Integer getRegistroByLoteSaidaQuarentenaEntreDatas(String lote, LocalDate dataInicio, LocalDate dataLimite) {
        final List<Registro> registrosList = registroRepository
                .findRegistroByLoteAndDataEntreDatas(lote, dataInicio, dataLimite);
        Integer totalSaidaQuarentena = 0;
        for (Registro registro : registrosList) {
            Integer saidaQuarentena = registro.getSaidaQuarentena();
            totalSaidaQuarentena += saidaQuarentena;
        }
        return totalSaidaQuarentena;
    }

    public Long create(final RegistroDTO registroDTO) {
        final Registro registro = new Registro();
        mapToEntity(registroDTO, registro);
        // Atualiza o saldo após registro
        Estoque estoque = registro.getEstoque();
        Integer quantidadeInicial = estoque.getQuantidade();
        Integer quantidadeFinal = (quantidadeInicial + registro.getEntradaQuantidade()) - registro.getSaidaQuantidade();
        Integer quarentenaInicial = estoque.getQuarentena();
        Integer quarentenaFinal = (quarentenaInicial + registro.getEntradaQuarentena()) - registro.getSaidaQuarentena();
        estoque.setQuantidade(quantidadeFinal);
        estoque.setQuarentena(quarentenaFinal);
        estoque.setSaldoAtual(quantidadeFinal + quarentenaFinal);
        estoqueRepository.save(estoque);
        registro.setSaldoQuantidadeInicial(quantidadeInicial);
        registro.setSaldoQuantidadeFinal(quantidadeFinal);
        registro.setSaldoQuarentenaInicial(quarentenaInicial);
        registro.setSaldoQuarentenaFinal(quarentenaFinal);
        return registroRepository.save(registro).getId();
    }

    public void update(final Long id, final RegistroDTO registroDTO) {
        final Registro registro = registroRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // Adicionar/remover do saldo apenas o que foi adicionado/removido em excesso
        Integer entradaQuarentena = registroDTO.getEntradaQuarentena() - registro.getEntradaQuarentena();
        Integer saidaQuarentena = registroDTO.getSaidaQuarentena() - registro.getSaidaQuarentena();
        Integer entradaQuantidade = registroDTO.getEntradaQuantidade() - registro.getEntradaQuantidade();
        Integer saidaQuantidade = registroDTO.getSaidaQuantidade() - registro.getSaidaQuantidade();
        mapToEntity(registroDTO, registro);
        // Atualiza o saldo após registro
        Estoque estoque = registro.getEstoque();
        Integer quantidadeInicial = estoque.getQuantidade();
        Integer quantidadeFinal = (quantidadeInicial + entradaQuantidade) - saidaQuantidade;
        Integer quarentenaInicial = estoque.getQuarentena();
        Integer quarentenaFinal = (quarentenaInicial + entradaQuarentena) - saidaQuarentena;
        estoque.setQuantidade(quantidadeFinal);
        estoque.setQuarentena(quarentenaFinal);
        estoque.setSaldoAtual(quantidadeFinal + quarentenaFinal);
        estoqueRepository.save(estoque);
        registro.setSaldoQuantidadeInicial(quantidadeInicial);
        registro.setSaldoQuantidadeFinal(quantidadeFinal);
        registro.setSaldoQuarentenaInicial(quarentenaInicial);
        registro.setSaldoQuarentenaFinal(quarentenaFinal);
        registroRepository.save(registro);
    }

    public void delete(final Long id) {
        registroRepository.deleteById(id);
    }

    private RegistroDTO mapToDTO(final Registro registro, final RegistroDTO registroDTO) {
        registroDTO.setId(registro.getId());
        registroDTO.setEntradaQuantidade(registro.getEntradaQuantidade());
        registroDTO.setEntradaQuarentena(registro.getEntradaQuarentena());
        registroDTO.setSaidaQuantidade(registro.getSaidaQuantidade());
        registroDTO.setSaidaQuarentena(registro.getSaidaQuarentena());
        registroDTO.setSaldoQuantidadeInicial(registro.getSaldoQuantidadeInicial());
        registroDTO.setSaldoQuantidadeFinal(registro.getSaldoQuantidadeFinal());
        registroDTO.setSaldoQuarentenaInicial(registro.getSaldoQuarentenaInicial());
        registroDTO.setSaldoQuarentenaFinal(registro.getSaldoQuarentenaFinal());
        registroDTO.setData(registro.getData());
        registroDTO.setEstoqueId(registro.getEstoque() == null ? null : registro.getEstoque().getId());
        registroDTO.setEstoque(registro.getEstoque());
        return registroDTO;
    }

    private Registro mapToEntity(final RegistroDTO registroDTO, final Registro registro) {
        registro.setId(registroDTO.getId());
        registro.setEntradaQuantidade(registroDTO.getEntradaQuantidade());
        registro.setEntradaQuarentena(registroDTO.getEntradaQuarentena());
        registro.setSaidaQuantidade(registroDTO.getSaidaQuantidade());
        registro.setSaidaQuarentena(registroDTO.getSaidaQuarentena());
        registro.setSaldoQuantidadeInicial(registroDTO.getSaldoQuantidadeInicial());
        registro.setSaldoQuantidadeFinal(registroDTO.getSaldoQuantidadeFinal());
        registro.setSaldoQuarentenaInicial(registroDTO.getSaldoQuarentenaInicial());
        registro.setSaldoQuarentenaFinal(registroDTO.getSaldoQuarentenaFinal());
        registro.setData(registroDTO.getData());
        final Estoque estoque = registroDTO.getEstoque() == null ? null : estoqueRepository.findById(registroDTO.getEstoqueId())
                .orElseThrow(() -> new NotFoundException("estoqueId not found"));
        registro.setEstoque(estoque);
        return registro;
    }

}
