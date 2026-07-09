package com.armario.adapter.outbound.persistence.mapper;

import com.armario.adapter.outbound.persistence.entity.UsuarioEntity;
import com.armario.domain.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioEntityMapper {

    UsuarioEntity toEntity(Usuario domain);

    Usuario toDomain(UsuarioEntity entity);
}
