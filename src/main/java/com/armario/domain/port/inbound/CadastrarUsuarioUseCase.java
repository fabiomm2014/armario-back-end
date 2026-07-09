package com.armario.domain.port.inbound;

import com.armario.domain.model.Usuario;

public interface CadastrarUsuarioUseCase {
    Usuario cadastrar(String login, String senha);
}
