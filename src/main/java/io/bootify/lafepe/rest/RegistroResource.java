package io.bootify.lafepe.rest;

import io.bootify.lafepe.model.EstoqueDTO;
import io.bootify.lafepe.model.ProdutoDTO;
import io.bootify.lafepe.model.RegistroDTO;
import io.bootify.lafepe.service.RegistroService;
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

    // GET registro by query
    @GetMapping("/query")
    public ResponseEntity<List<RegistroDTO>> getRegistroByQuery(@RequestParam Map<String, String> customQuery){
        switch (customQuery.keySet().toString()){
            case "[entrada, operador]" -> {
                Integer entrada = Integer.valueOf(customQuery.get("entrada"));
                String operador = customQuery.get("operador");
                List<RegistroDTO> registroDTOList = switch (operador) {
                    case "EqualTo" -> registroService.getRegistroByEntrada(entrada);
                    case "LessThan" -> registroService.getRegistroByEntradaLessThan(entrada);
                    case "LessThanOrEqualTo" -> registroService.getRegistroByEntradaLessThanOrEqualTo(entrada);
                    case "GreaterThan" -> registroService.getRegistroByEntradaGreaterThan(entrada);
                    case "GreaterThanOrEqualTo" -> registroService.getRegistroByEntradaGreaterThanOrEqualTo(entrada);
                    default -> registroService.findAll();
                };
                return ResponseEntity.ok(registroDTOList);
            }
            case "[saida, operador]" -> {
                Integer saida = Integer.valueOf(customQuery.get("saida"));
                String operador = customQuery.get("operador");
                List<RegistroDTO> registroDTOList = switch (operador) {
                    case "EqualTo" -> registroService.getRegistroBySaida(saida);
                    case "LessThan" -> registroService.getRegistroBySaidaLessThan(saida);
                    case "LessThanOrEqualTo" -> registroService.getRegistroBySaidaLessThanOrEqualTo(saida);
                    case "GreaterThan" -> registroService.getRegistroBySaidaGreaterThan(saida);
                    case "GreaterThanOrEqualTo" -> registroService.getRegistroBySaidaGreaterThanOrEqualTo(saida);
                    default -> registroService.findAll();
                };
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
