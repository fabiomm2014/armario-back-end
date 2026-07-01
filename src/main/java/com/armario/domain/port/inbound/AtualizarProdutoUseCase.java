package com.armario.domain.port.inbound;

import com.armario.domain.model.Produto;

import java.util.UUID;

public interface AtualizarProdutoUseCase {
    Produto atualizar(UUID id, Produto produto);
}
