package com.armario.config;

import com.armario.app.usecase.ProdutoService;
import com.armario.app.usecase.UsuarioService;
import com.armario.domain.port.outbound.ProdutoRepositoryPort;
import com.armario.domain.port.outbound.TokenProviderPort;
import com.armario.domain.port.outbound.UsuarioRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {

    @Bean
    public ProdutoService produtoService(ProdutoRepositoryPort repositoryPort) {
        return new ProdutoService(repositoryPort);
    }

    @Bean
    public UsuarioService usuarioService(UsuarioRepositoryPort repositoryPort,
                                         PasswordEncoder passwordEncoder,
                                         TokenProviderPort tokenProviderPort) {
        return new UsuarioService(repositoryPort, passwordEncoder, tokenProviderPort);
    }
}
