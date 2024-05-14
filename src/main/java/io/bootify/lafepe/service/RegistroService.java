package io.bootify.lafepe.service;

import io.bootify.lafepe.domain.Estoque;
import io.bootify.lafepe.domain.Registro;
import io.bootify.lafepe.model.RegistroDTO;
import io.bootify.lafepe.repos.EstoqueRepository;
import io.bootify.lafepe.repos.RegistroRepository;
import io.bootify.lafepe.util.NotFoundException;

import java.time.LocalDate;
import java.util.List;
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

    public List<RegistroDTO> getRegistroByEntrada(Integer entrada) {
        final List<Registro> registros = registroRepository.findAllByEntrada(entrada);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroByEntradaLessThan(Integer entrada) {
        final List<Registro> registros = registroRepository.findAllByEntradaLessThan(entrada);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroByEntradaLessThanOrEqualTo(Integer entrada) {
        final List<Registro> registros = registroRepository.findAllByEntradaLessThanOrEqualTo(entrada);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroByEntradaGreaterThan(Integer entrada) {
        final List<Registro> registros = registroRepository.findAllByEntradaGreaterThan(entrada);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroByEntradaGreaterThanOrEqualTo(Integer entrada) {
        final List<Registro> registros = registroRepository.findAllByEntradaGreaterThanOrEqualTo(entrada);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroBySaida(Integer saida) {
        final List<Registro> registros = registroRepository.findAllBySaida(saida);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroBySaidaLessThan(Integer saida) {
        final List<Registro> registros = registroRepository.findAllBySaidaLessThan(saida);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroBySaidaLessThanOrEqualTo(Integer saida) {
        final List<Registro> registros = registroRepository.findAllBySaidaLessThanOrEqualTo(saida);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroBySaidaGreaterThan(Integer saida) {
        final List<Registro> registros = registroRepository.findAllBySaidaGreaterThan(saida);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroBySaidaGreaterThanOrEqualTo(Integer saida) {
        final List<Registro> registros = registroRepository.findAllBySaidaGreaterThanOrEqualTo(saida);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroBySaldo(Integer saldo) {
        final List<Registro> registros = registroRepository.findAllBySaldo(saldo);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroBySaldoLessThan(Integer saldo) {
        final List<Registro> registros = registroRepository.findAllBySaldoLessThan(saldo);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroBySaldoLessThanOrEqualTo(Integer saldo) {
        final List<Registro> registros = registroRepository.findAllBySaldoLessThanOrEqualTo(saldo);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroBySaldoGreaterThan(Integer saldo) {
        final List<Registro> registros = registroRepository.findAllBySaldoGreaterThan(saldo);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public List<RegistroDTO> getRegistroBySaldoGreaterThanOrEqualTo(Integer saldo) {
        final List<Registro> registros = registroRepository.findAllBySaldoGreaterThanOrEqualTo(saldo);
        return registros.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
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

    public Long create(final RegistroDTO registroDTO) {
        final Registro registro = new Registro();
        mapToEntity(registroDTO, registro);
        return registroRepository.save(registro).getId();
    }

    public void update(final Long id, final RegistroDTO registroDTO) {
        final Registro registro = registroRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(registroDTO, registro);
        registroRepository.save(registro);
    }

    public void delete(final Long id) {
        registroRepository.deleteById(id);
    }

    private RegistroDTO mapToDTO(final Registro registro, final RegistroDTO registroDTO) {
        registroDTO.setId(registro.getId());
        registroDTO.setEntrada(registro.getEntrada());
        registroDTO.setSaida(registro.getSaida());
        registroDTO.setSaldo(registro.getSaldo());
        registroDTO.setData(registro.getData());
        registroDTO.setEstoque(registro.getEstoque() == null ? null : registro.getEstoque().getId());
        return registroDTO;
    }

    private Registro mapToEntity(final RegistroDTO registroDTO, final Registro registro) {
        registro.setEntrada(registroDTO.getEntrada());
        registro.setSaida(registroDTO.getSaida());
        registro.setSaldo(registroDTO.getSaldo());
        registro.setData(registroDTO.getData());
        final Estoque estoqueId = registroDTO.getEstoque() == null ? null : estoqueRepository.findById(registroDTO.getEstoque())
                .orElseThrow(() -> new NotFoundException("estoqueId not found"));
        registro.setEstoque(estoqueId);
        return registro;
    }

}
