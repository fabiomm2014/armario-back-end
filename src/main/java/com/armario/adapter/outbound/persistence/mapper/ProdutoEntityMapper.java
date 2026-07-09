package com.armario.adapter.outbound.persistence.mapper;

import com.armario.adapter.outbound.persistence.entity.ProdutoEntity;
import com.armario.domain.model.Produto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoEntityMapper {

    ProdutoEntity toEntity(Produto domain);

    Produto toDomain(ProdutoEntity entity);
}
