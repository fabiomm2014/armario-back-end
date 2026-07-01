package com.armario.adapter.inbound.mapper;

import com.armario.adapter.inbound.dto.ProdutoRequest;
import com.armario.adapter.inbound.dto.ProdutoResponse;
import com.armario.domain.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoDtoMapper {

    @Mapping(target = "id", ignore = true)
    Produto toDomain(ProdutoRequest request);

    ProdutoResponse toResponse(Produto produto);

    List<ProdutoResponse> toResponseList(List<Produto> produtos);
}
