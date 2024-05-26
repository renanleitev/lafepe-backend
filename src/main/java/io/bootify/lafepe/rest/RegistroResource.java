package io.bootify.lafepe.rest;

import io.bootify.lafepe.model.EstoqueDTO;
import io.bootify.lafepe.model.ProdutoDTO;
import io.bootify.lafepe.model.RegistroDTO;
import io.bootify.lafepe.service.RegistroService;
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
@RequestMapping(value = "/api/registros", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistroResource {

    private final RegistroService registroService;

    public RegistroResource(final RegistroService registroService) {
        this.registroService = registroService;
    }

    @GetMapping
    public ResponseEntity<List<RegistroDTO>> getAllRegistros() {
        return ResponseEntity.ok(registroService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroDTO> getRegistro(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(registroService.get(id));
    }

    @GetMapping("/estoque/{id}")
    public ResponseEntity<List<RegistroDTO>> getRegistroByEstoqueId(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(registroService.getRegistroByEstoqueId(id));
    }

    // GET registro by query
    @GetMapping("/query")
    public ResponseEntity<List<RegistroDTO>> getRegistroByQuery(@RequestParam Map<String, String> customQuery){
        switch (customQuery.keySet().toString()){
            case "[lote]" -> {
                String lote = customQuery.get("lote");
                List<RegistroDTO> registroDTOList = registroService.getRegistroByEstoqueLote(lote);
                return ResponseEntity.ok(registroDTOList);
            }
            case "[data, operador]" -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate data = LocalDate.parse(customQuery.get("data"), formatter);
                String operador = customQuery.get("operador");
                List<RegistroDTO> registroDTOList = switch (operador) {
                    case "EqualTo" -> registroService.getRegistroByData(data);
                    case "LessThan" -> registroService.getRegistroByDataLessThan(data);
                    case "LessThanOrEqualTo" -> registroService.getRegistroByDataLessThanOrEqualTo(data);
                    case "GreaterThan" -> registroService.getRegistroByDataGreaterThan(data);
                    case "GreaterThanOrEqualTo" -> registroService.getRegistroByDataGreaterThanOrEqualTo(data);
                    default -> registroService.findAll();
                };
                return ResponseEntity.ok(registroDTOList);
            }
            default -> {return ResponseEntity.ok(registroService.findAll()); }
        }
    }

    @GetMapping("/quarentena/entrada/periodo/{dataInicio}/{dataLimite}")
    public ResponseEntity<List<Integer>>
    getEntradaQuarentenaPeriodoEntreDatas(
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

            Integer quantidadeProdutos = registroService.getRegistroEntradaQuarentena(
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

    @GetMapping("/quarentena/saida/periodo/{dataInicio}/{dataLimite}")
    public ResponseEntity<List<Integer>>
    getSaidaQuarentenaPeriodoEntreDatas(
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

            Integer quantidadeProdutos = registroService.getRegistroSaidaQuarentena(
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

    @GetMapping("/quantidade/entrada/periodo/{dataInicio}/{dataLimite}")
    public ResponseEntity<List<Integer>>
    getEntradaQuantidadePeriodoEntreDatas(
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

            Integer quantidadeProdutos = registroService.getRegistroEntradaQuantidade(
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

    @GetMapping("/quantidade/saida/periodo/{dataInicio}/{dataLimite}")
    public ResponseEntity<List<Integer>>
    getSaidaQuantidadePeriodoEntreDatas(
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

            Integer quantidadeProdutos = registroService.getRegistroSaidaQuantidade(
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

    @GetMapping("/quantidade/entrada/lote/{lote}/periodo/{dataInicio}/{dataLimite}")
    public ResponseEntity<List<Integer>>
    getRegistroByLoteEntradaQuantidadePeriodoEntreDatas(
            @PathVariable(name = "lote") final String lote,
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

            Integer quantidadeProdutos = registroService.getRegistroByLoteEntradaQuantidadeEntreDatas(
                    lote,
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

    @GetMapping("/quantidade/saida/lote/{lote}/periodo/{dataInicio}/{dataLimite}")
    public ResponseEntity<List<Integer>>
    getRegistroByLoteSaidaQuantidadePeriodoEntreDatas(
            @PathVariable(name = "lote") final String lote,
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

            Integer quantidadeProdutos = registroService.getRegistroByLoteSaidaQuantidadeEntreDatas(
                    lote,
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

    @GetMapping("/quarentena/entrada/lote/{lote}/periodo/{dataInicio}/{dataLimite}")
    public ResponseEntity<List<Integer>>
    getRegistroByLoteEntradaQuarentenaPeriodoEntreDatas(
            @PathVariable(name = "lote") final String lote,
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

            Integer quantidadeProdutos = registroService.getRegistroByLoteEntradaQuarentenaEntreDatas(
                    lote,
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

    @GetMapping("/quarentena/saida/lote/{lote}/periodo/{dataInicio}/{dataLimite}")
    public ResponseEntity<List<Integer>>
    getRegistroByLoteSaidaQuarentenaPeriodoEntreDatas(
            @PathVariable(name = "lote") final String lote,
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

            Integer quantidadeProdutos = registroService.getRegistroByLoteSaidaQuarentenaEntreDatas(
                    lote,
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

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRegistro(@RequestBody @Valid final RegistroDTO registroDTO) {
        final Long createdId = registroService.create(registroDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateRegistro(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final RegistroDTO registroDTO) {
        registroService.update(id, registroDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRegistro(@PathVariable(name = "id") final Long id) {
        registroService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
