package com.armario.domain.port.inbound;

import com.armario.domain.model.Produto;

public interface SalvarProdutoUseCase {
    Produto salvar(Produto produto);
}
