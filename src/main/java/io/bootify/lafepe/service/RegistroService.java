package io.bootify.lafepe.service;

import io.bootify.lafepe.domain.Estoque;
import io.bootify.lafepe.domain.Registro;
import io.bootify.lafepe.model.RegistroDTO;
import io.bootify.lafepe.repos.EstoqueRepository;
import io.bootify.lafepe.repos.RegistroRepository;
import io.bootify.lafepe.util.NotFoundException;
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
        final List<Registro> registroes = registroRepository.findAll(Sort.by("id"));
        return registroes.stream()
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .toList();
    }

    public RegistroDTO get(final Long id) {
        return registroRepository.findById(id)
                .map(registro -> mapToDTO(registro, new RegistroDTO()))
                .orElseThrow(NotFoundException::new);
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
        registroDTO.setEstoqueId(registro.getEstoqueId() == null ? null : registro.getEstoqueId().getId());
        return registroDTO;
    }

    private Registro mapToEntity(final RegistroDTO registroDTO, final Registro registro) {
        registro.setEntrada(registroDTO.getEntrada());
        registro.setSaida(registroDTO.getSaida());
        registro.setSaldo(registroDTO.getSaldo());
        registro.setData(registroDTO.getData());
        final Estoque estoqueId = registroDTO.getEstoqueId() == null ? null : estoqueRepository.findById(registroDTO.getEstoqueId())
                .orElseThrow(() -> new NotFoundException("estoqueId not found"));
        registro.setEstoqueId(estoqueId);
        return registro;
    }

}
