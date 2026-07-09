package com.armario.app.usecase;

import com.armario.domain.model.Usuario;
import com.armario.domain.port.inbound.AutenticarUsuarioUseCase;
import com.armario.domain.port.inbound.CadastrarUsuarioUseCase;
import com.armario.domain.port.outbound.TokenProviderPort;
import com.armario.domain.port.outbound.UsuarioRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public class UsuarioService implements CadastrarUsuarioUseCase, AutenticarUsuarioUseCase {

    private final UsuarioRepositoryPort repositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final TokenProviderPort tokenProviderPort;

    public UsuarioService(UsuarioRepositoryPort repositoryPort,
                          PasswordEncoder passwordEncoder,
                          TokenProviderPort tokenProviderPort) {
        this.repositoryPort = repositoryPort;
        this.passwordEncoder = passwordEncoder;
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    public Usuario cadastrar(String login, String senha) {
        if (repositoryPort.existePorLogin(login)) {
            throw new RuntimeException("Login ja cadastrado: " + login);
        }
        Usuario usuario = new Usuario(UUID.randomUUID(), login, passwordEncoder.encode(senha));
        return repositoryPort.salvar(usuario);
    }

    @Override
    public String autenticar(String login, String senha) {
        Usuario usuario = repositoryPort.buscarPorLogin(login)
                .orElseThrow(() -> new RuntimeException("Credenciais invalidas"));
        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Credenciais invalidas");
        }
        return tokenProviderPort.gerarToken(usuario.getLogin());
    }
}
