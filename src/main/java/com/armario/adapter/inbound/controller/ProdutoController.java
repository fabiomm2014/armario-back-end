package com.armario.adapter.inbound.controller;

import com.armario.adapter.inbound.dto.ProdutoRequest;
import com.armario.adapter.inbound.dto.ProdutoResponse;
import com.armario.adapter.inbound.mapper.ProdutoDtoMapper;
import com.armario.domain.model.Produto;
import com.armario.domain.port.inbound.AtualizarProdutoUseCase;
import com.armario.domain.port.inbound.BuscarProdutoUseCase;
import com.armario.domain.port.inbound.DeletarProdutoUseCase;
import com.armario.domain.port.inbound.SalvarProdutoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final SalvarProdutoUseCase salvarUseCase;
    private final BuscarProdutoUseCase buscarUseCase;
    private final AtualizarProdutoUseCase atualizarUseCase;
    private final DeletarProdutoUseCase deletarUseCase;
    private final ProdutoDtoMapper mapper;

    public ProdutoController(SalvarProdutoUseCase salvarUseCase,
                             BuscarProdutoUseCase buscarUseCase,
                             AtualizarProdutoUseCase atualizarUseCase,
                             DeletarProdutoUseCase deletarUseCase,
                             ProdutoDtoMapper mapper) {
        this.salvarUseCase = salvarUseCase;
        this.buscarUseCase = buscarUseCase;
        this.atualizarUseCase = atualizarUseCase;
        this.deletarUseCase = deletarUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> salvar(@RequestBody ProdutoRequest request) {
        Produto produto = mapper.toDomain(request);
        Produto salvo = salvarUseCase.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(salvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable UUID id) {
        return buscarUseCase.buscarPorId(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> buscarTodos() {
        return ResponseEntity.ok(mapper.toResponseList(buscarUseCase.buscarTodos()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable UUID id,
                                                     @RequestBody ProdutoRequest request) {
        Produto produto = mapper.toDomain(request);
        Produto atualizado = atualizarUseCase.atualizar(id, produto);
        return ResponseEntity.ok(mapper.toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
