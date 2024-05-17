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
