package com.armario.domain.port.inbound;

import com.armario.domain.model.Produto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BuscarProdutoUseCase {
    Optional<Produto> buscarPorId(UUID id);
    List<Produto> buscarTodos();
}
