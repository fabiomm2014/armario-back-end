package com.armario.domain.port.inbound;

public interface AutenticarUsuarioUseCase {
    String autenticar(String login, String senha);
}
