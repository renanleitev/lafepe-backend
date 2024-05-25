package io.bootify.lafepe.rest;

import io.bootify.lafepe.model.EstoqueDTO;
import io.bootify.lafepe.model.RegistroDTO;
import io.bootify.lafepe.service.EstoqueService;
import io.bootify.lafepe.util.ReferencedException;
import io.bootify.lafepe.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = {
        "Authorization",
        "Origin",
        "x-total-pages",
        "x-total-count",
})
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

    @GetMapping("/produto/{id}")
    public ResponseEntity<List<EstoqueDTO>> getEstoqueByProdutoId(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(estoqueService.getEstoqueByProdutoId(id));
    }

    @GetMapping("/validade/vencidos")
    public ResponseEntity<List<EstoqueDTO>> getEstoqueByValidadeVencidos() {
        return ResponseEntity.ok(estoqueService.getEstoqueByValidadeVencidos());
    }

    @GetMapping("/validade/{periodo}")
    public ResponseEntity<List<EstoqueDTO>> getEstoqueByValidadePeriodo(@PathVariable(name = "periodo") final Integer periodo) {
        return ResponseEntity.ok(estoqueService.getEstoqueByValidadePeriodo(periodo));
    }

    @GetMapping("/validade/prejuizo/{dataInicio}/{dataLimite}")
    public ResponseEntity<Double>
        getEstoqueByPrejuizoValidadePeriodoEntreDatas(
                @PathVariable(name = "dataInicio") final LocalDate dataInicio,
                @PathVariable(name = "dataLimite") final LocalDate dataLimite
                ) {
        return ResponseEntity.ok(estoqueService.getEstoquePrejuizoValidadeEntreDatas(dataInicio, dataLimite));
    }

    @GetMapping("/validade/prejuizo/periodo/{dataInicio}/{dataLimite}")
    public ResponseEntity<List<Double>>
    getEstoqueByPrejuizoValidadePeriodoMeses(
            @PathVariable(name = "dataInicio") final LocalDate dataInicio,
            @PathVariable(name = "dataLimite") final LocalDate dataLimite
    ) {
        Date dataInicioDate = Date.from(dataInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dataLimiteDate = Date.from(dataLimite.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(dataInicioDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(dataLimiteDate);

        List<Double> prejuizoMensalLista = new ArrayList<>();

        while (startCal.before(endCal)) {
            Calendar monthStart = (Calendar) startCal.clone();
            monthStart.set(Calendar.DAY_OF_MONTH, 1);

            Calendar monthEnd = (Calendar) startCal.clone();
            monthEnd.set(Calendar.DAY_OF_MONTH, monthEnd.getActualMaximum(Calendar.DAY_OF_MONTH));

            if (monthEnd.after(endCal)) {
                monthEnd.setTime(dataLimiteDate);
            }

            Double prejuizoMensal = estoqueService.getEstoquePrejuizoValidadeEntreDatas(
                    monthStart.getTime().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate(),
                    monthEnd.getTime().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
            );
            prejuizoMensalLista.add(prejuizoMensal);
            // Move para o próximo mês
            startCal.add(Calendar.MONTH, 1);
        }
        return ResponseEntity.ok(prejuizoMensalLista);
    }

    @GetMapping("/validade/quantidade/periodo/{dataInicio}/{dataLimite}")
    public ResponseEntity<List<Integer>>
    getEstoqueByPrejuizoQuantidadePeriodoMeses(
            @PathVariable(name = "dataInicio") final LocalDate dataInicio,
            @PathVariable(name = "dataLimite") final LocalDate dataLimite
    ) {
        Date dataInicioDate = Date.from(dataInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dataLimiteDate = Date.from(dataLimite.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(dataInicioDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(dataLimiteDate);

        List<Integer> quantidadeMensalLista = new ArrayList<>();

        while (startCal.before(endCal)) {
            Calendar monthStart = (Calendar) startCal.clone();
            monthStart.set(Calendar.DAY_OF_MONTH, 1);

            Calendar monthEnd = (Calendar) startCal.clone();
            monthEnd.set(Calendar.DAY_OF_MONTH, monthEnd.getActualMaximum(Calendar.DAY_OF_MONTH));

            if (monthEnd.after(endCal)) {
                monthEnd.setTime(dataLimiteDate);
            }

            Integer quantidadeProdutos = estoqueService.getEstoqueQuantidadeValidadeEntreDatas(
                    monthStart.getTime().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate(),
                    monthEnd.getTime().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
            );
            quantidadeMensalLista.add(quantidadeProdutos);
            // Move para o próximo mês
            startCal.add(Calendar.MONTH, 1);
        }
        return ResponseEntity.ok(quantidadeMensalLista);
    }

    @GetMapping("/validade/prejuizo/saldoAtual")
    public ResponseEntity<Double> getEstoqueByPrejuizoSaldoAtual() {
        return ResponseEntity.ok(estoqueService.getEstoquePrejuizoSaldoAtual());
    }

    @GetMapping("/validade/prejuizo/saldoOriginal")
    public ResponseEntity<Double> getEstoqueByPrejuizoSaldoOriginal() {
        return ResponseEntity.ok(estoqueService.getEstoquePrejuizoSaldoOriginal());
    }

    // GET estoque by query
    @GetMapping("/query")
    public ResponseEntity<List<EstoqueDTO>> getEstoqueByQuery(@RequestParam Map<String, String> customQuery){
        switch (customQuery.keySet().toString()){
            case "[saldoOriginal, operador]" -> {
                Integer saldo = Integer.valueOf(customQuery.get("saldo"));
                String operador = customQuery.get("operador");
                List<EstoqueDTO> estoqueDTOList = switch (operador) {
                    case "EqualTo" -> estoqueService.getEstoqueBySaldoOriginal(saldo);
                    case "LessThan" -> estoqueService.getEstoqueBySaldoOriginalLessThan(saldo);
                    case "LessThanOrEqualTo" -> estoqueService.getEstoqueBySaldoOriginalLessThanOrEqualTo(saldo);
                    case "GreaterThan" -> estoqueService.getEstoqueBySaldoOriginalGreaterThan(saldo);
                    case "GreaterThanOrEqualTo" -> estoqueService.getEstoqueBySaldoOriginalGreaterThanOrEqualTo(saldo);
                    default -> estoqueService.findAll();
                };
                return ResponseEntity.ok(estoqueDTOList);
            }
            case "[saldoAtual, operador]" -> {
                Integer saldo = Integer.valueOf(customQuery.get("saldo"));
                String operador = customQuery.get("operador");
                List<EstoqueDTO> estoqueDTOList = switch (operador) {
                    case "EqualTo" -> estoqueService.getEstoqueBySaldoAtual(saldo);
                    case "LessThan" -> estoqueService.getEstoqueBySaldoAtualLessThan(saldo);
                    case "LessThanOrEqualTo" -> estoqueService.getEstoqueBySaldoAtualLessThanOrEqualTo(saldo);
                    case "GreaterThan" -> estoqueService.getEstoqueBySaldoAtualGreaterThan(saldo);
                    case "GreaterThanOrEqualTo" -> estoqueService.getEstoqueBySaldoAtualGreaterThanOrEqualTo(saldo);
                    default -> estoqueService.findAll();
                };
                return ResponseEntity.ok(estoqueDTOList);
            }
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
