package com.armario.domain.port.outbound;

import com.armario.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepositoryPort {
    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorLogin(String login);
    boolean existePorLogin(String login);
}
