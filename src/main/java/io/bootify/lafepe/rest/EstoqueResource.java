package io.bootify.lafepe.rest;

import io.bootify.lafepe.model.EstoqueDTO;
import io.bootify.lafepe.service.EstoqueService;
import io.bootify.lafepe.util.ReferencedException;
import io.bootify.lafepe.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
