package io.bootify.lafepe.rest;

import io.bootify.lafepe.model.EstoqueDTO;
import io.bootify.lafepe.service.EstoqueService;
import io.bootify.lafepe.util.ReferencedException;
import io.bootify.lafepe.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/estoques", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstoqueResource {

    private final EstoqueService estoqueService;

    public EstoqueResource(final EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping
    public ResponseEntity<List<EstoqueDTO>> getAllEstoques() {
        return ResponseEntity.ok(estoqueService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueDTO> getEstoque(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(estoqueService.get(id));
    }

    // GET estoque by query
    @GetMapping("/query")
    public ResponseEntity<List<EstoqueDTO>> getEstoqueByQuery(@RequestParam Map<String, String> customQuery){
        switch (customQuery.keySet().toString()){
            case "[quantidade, operador]" -> {
                Integer quantidade = Integer.valueOf(customQuery.get("quantidade"));
                String operador = customQuery.get("operador");
                List<EstoqueDTO> estoqueDTOList = switch (operador) {
                    case "EqualTo" -> estoqueService.getEstoqueByQuantidade(quantidade);
                    case "LessThan" -> estoqueService.getEstoqueByQuantidadeLessThan(quantidade);
                    case "LessThanOrEqualTo" -> estoqueService.getEstoqueByQuantidadeLessThanOrEqualTo(quantidade);
                    case "GreaterThan" -> estoqueService.getEstoqueByQuantidadeGreaterThan(quantidade);
                    case "GreaterThanOrEqualTo" -> estoqueService.getEstoqueByQuantidadeGreaterThanOrEqualTo(quantidade);
                    default -> estoqueService.findAll();
                };
                return ResponseEntity.ok(estoqueDTOList);
            }
            case "[quarentena, operador]" -> {
                Integer quarentena = Integer.valueOf(customQuery.get("quarentena"));
                String operador = customQuery.get("operador");
                List<EstoqueDTO> estoqueDTOList = switch (operador) {
                    case "EqualTo" -> estoqueService.getEstoqueByQuarentena(quarentena);
                    case "LessThan" -> estoqueService.getEstoqueByQuarentenaLessThan(quarentena);
                    case "LessThanOrEqualTo" -> estoqueService.getEstoqueByQuarentenaLessThanOrEqualTo(quarentena);
                    case "GreaterThan" -> estoqueService.getEstoqueByQuarentenaGreaterThan(quarentena);
                    case "GreaterThanOrEqualTo" -> estoqueService.getEstoqueByQuarentenaGreaterThanOrEqualTo(quarentena);
                    default -> estoqueService.findAll();
                };
                return ResponseEntity.ok(estoqueDTOList);
            }
            case "[lote]" -> {
                String lote = customQuery.get("lote");
                List<EstoqueDTO> estoqueDTOList = estoqueService.getEstoqueByLoteLike(lote);
                return ResponseEntity.ok(estoqueDTOList);
            }
            case "[descricao]" -> {
                String descricao = customQuery.get("descricao");
                List<EstoqueDTO> estoqueDTOList = estoqueService.getEstoqueByDescricaoLike(descricao);
                return ResponseEntity.ok(estoqueDTOList);
            }
            case "[unidade]" -> {
                String unidade = customQuery.get("unidade");
                List<EstoqueDTO> estoqueDTOList = estoqueService.getEstoqueByUnidadeLike(unidade);
                return ResponseEntity.ok(estoqueDTOList);
            }
            case "[validade, operador]" -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate validade = LocalDate.parse(customQuery.get("validade"), formatter);
                String operador = customQuery.get("operador");
                List<EstoqueDTO> estoqueDTOList = switch (operador) {
                    case "EqualTo" -> estoqueService.getEstoqueByValidade(validade);
                    case "LessThan" -> estoqueService.getEstoqueByValidadeLessThan(validade);
                    case "LessThanOrEqualTo" -> estoqueService.getEstoqueByValidadeLessThanOrEqualTo(validade);
                    case "GreaterThan" -> estoqueService.getEstoqueByValidadeGreaterThan(validade);
                    case "GreaterThanOrEqualTo" -> estoqueService.getEstoqueByValidadeGreaterThanOrEqualTo(validade);
                    default -> estoqueService.findAll();
                };
                return ResponseEntity.ok(estoqueDTOList);
            }
            default -> {
                return ResponseEntity.ok(estoqueService.findAll());
            }
        }
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEstoque(@RequestBody @Valid final EstoqueDTO estoqueDTO) {
        final Long createdId = estoqueService.create(estoqueDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateEstoque(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final EstoqueDTO estoqueDTO) {
        estoqueService.update(id, estoqueDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEstoque(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = estoqueService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        estoqueService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
