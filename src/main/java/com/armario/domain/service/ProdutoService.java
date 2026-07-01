package com.armario.domain.service;

import com.armario.domain.model.Produto;
import com.armario.domain.port.inbound.BuscarProdutoUseCase;
import com.armario.domain.port.inbound.SalvarProdutoUseCase;
import com.armario.domain.port.outbound.ProdutoRepositoryPort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProdutoService implements SalvarProdutoUseCase, BuscarProdutoUseCase {

    private final ProdutoRepositoryPort repositoryPort;

    public ProdutoService(ProdutoRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Produto salvar(Produto produto) {
        if (produto.getId() == null) {
            produto.setId(UUID.randomUUID());
        }
        return repositoryPort.salvar(produto);
    }

    @Override
    public Optional<Produto> buscarPorId(UUID id) {
        return repositoryPort.buscarPorId(id);
    }

    @Override
    public List<Produto> buscarTodos() {
        return repositoryPort.buscarTodos();
    }
}
