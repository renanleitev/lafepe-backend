package io.bootify.lafepe.rest;

import io.bootify.lafepe.model.ProdutoDTO;
import io.bootify.lafepe.service.ProdutoService;
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
@RequestMapping(value = "/api/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProdutoResource {

    private final ProdutoService produtoService;

    public ProdutoResource(final ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> getAllProdutos() {
        return ResponseEntity.ok(produtoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> getProduto(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(produtoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createProduto(@RequestBody @Valid final ProdutoDTO produtoDTO) {
        final Long createdId = produtoService.create(produtoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateProduto(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ProdutoDTO produtoDTO) {
        produtoService.update(id, produtoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProduto(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = produtoService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
