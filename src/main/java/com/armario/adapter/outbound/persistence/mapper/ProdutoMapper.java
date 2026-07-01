package com.armario.adapter.outbound.persistence.mapper;

import com.armario.adapter.outbound.persistence.entity.ProdutoEntity;
import com.armario.domain.model.Produto;

public class ProdutoMapper {

    private ProdutoMapper() {
    }

    public static ProdutoEntity toEntity(Produto domain) {
        return new ProdutoEntity(
                domain.getId(),
                domain.getNome(),
                domain.getPeso(),
                domain.getTipo(),
                domain.getDataFabricacao(),
                domain.getDataValidade()
        );
    }

    public static Produto toDomain(ProdutoEntity entity) {
        return new Produto(
                entity.getId(),
                entity.getNome(),
                entity.getPeso(),
                entity.getTipo(),
                entity.getDataFabricacao(),
                entity.getDataValidade()
        );
    }
}
