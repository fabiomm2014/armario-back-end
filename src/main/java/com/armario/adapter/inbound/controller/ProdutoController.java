package com.armario.adapter.inbound.controller;

import com.armario.domain.model.Produto;
import com.armario.domain.port.inbound.BuscarProdutoUseCase;
import com.armario.domain.port.inbound.SalvarProdutoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    public ProdutoController(SalvarProdutoUseCase salvarUseCase,
                             BuscarProdutoUseCase buscarUseCase) {
        this.salvarUseCase = salvarUseCase;
        this.buscarUseCase = buscarUseCase;
    }

    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
        Produto salvo = salvarUseCase.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable UUID id) {
        return buscarUseCase.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Produto>> buscarTodos() {
        return ResponseEntity.ok(buscarUseCase.buscarTodos());
    }
}
