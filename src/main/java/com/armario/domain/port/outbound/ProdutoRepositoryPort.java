package com.armario.domain.port.outbound;

import com.armario.domain.model.Produto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepositoryPort {
    Produto salvar(Produto produto);
    Optional<Produto> buscarPorId(UUID id);
    List<Produto> buscarTodos();
}
