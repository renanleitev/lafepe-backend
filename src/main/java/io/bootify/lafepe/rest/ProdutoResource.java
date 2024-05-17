package io.bootify.lafepe.rest;

import io.bootify.lafepe.model.EstoqueDTO;
import io.bootify.lafepe.model.ProdutoDTO;
import io.bootify.lafepe.service.ProdutoService;
import io.bootify.lafepe.util.ReferencedException;
import io.bootify.lafepe.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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

    // GET produto by query
    @GetMapping("/query")
    public ResponseEntity<List<ProdutoDTO>> getProdutoByQuery(@RequestParam Map<String, String> customQuery){
        switch (customQuery.keySet().toString()){
            case "[precoUnitario, operador]" -> {
                Double precoUnitario = Double.valueOf(customQuery.get("precoUnitario"));
                String operador = customQuery.get("operador");
                List<ProdutoDTO> produtoDTOList = switch (operador) {
                    case "EqualTo" -> produtoService.getProdutoByPrecoUnitario(precoUnitario);
                    case "LessThan" -> produtoService.getProdutoByPrecoUnitarioLessThan(precoUnitario);
                    case "LessThanOrEqualTo" -> produtoService.getProdutoByPrecoUnitarioLessThanOrEqualTo(precoUnitario);
                    case "GreaterThan" -> produtoService.getProdutoByPrecoUnitarioGreaterThan(precoUnitario);
                    case "GreaterThanOrEqualTo" -> produtoService.getProdutoByPrecoUnitarioGreaterThanOrEqualTo(precoUnitario);
                    default -> produtoService.findAll();
                };
                return ResponseEntity.ok(produtoDTOList);
            }
            case "[nome]" -> {
                String nome = customQuery.get("nome");
                List<ProdutoDTO> produtoDTOList = produtoService.getProdutoByNomeLike(nome);
                return ResponseEntity.ok(produtoDTOList);
            }
            case "[codigo]" -> {
                String codigo = customQuery.get("codigo");
                List<ProdutoDTO> produtoDTOList = produtoService.getProdutoByCodigoLike(codigo);
                return ResponseEntity.ok(produtoDTOList);
            }
            case "[fabricante]" -> {
                String fabricante = customQuery.get("fabricante");
                List<ProdutoDTO> produtoDTOList = produtoService.getProdutoByFabricanteLike(fabricante);
                return ResponseEntity.ok(produtoDTOList);
            }
            default -> { return ResponseEntity.ok(produtoService.findAll()); }
        }
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
