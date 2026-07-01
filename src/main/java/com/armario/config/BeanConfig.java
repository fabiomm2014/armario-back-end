package com.armario.config;

import com.armario.config.security.JwtTokenProvider;
import com.armario.domain.port.outbound.ProdutoRepositoryPort;
import com.armario.domain.port.outbound.UsuarioRepositoryPort;
import com.armario.domain.service.ProdutoService;
import com.armario.domain.service.UsuarioService;
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
                                         JwtTokenProvider jwtTokenProvider) {
        return new UsuarioService(repositoryPort, passwordEncoder, jwtTokenProvider);
    }
}
