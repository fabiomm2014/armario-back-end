package com.armario.adapter.inbound.mapper;

import com.armario.adapter.inbound.dto.CadastroResponse;
import com.armario.domain.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioDtoMapper {

    CadastroResponse toResponse(Usuario usuario);
}
