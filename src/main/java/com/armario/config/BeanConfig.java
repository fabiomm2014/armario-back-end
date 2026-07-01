package com.armario.config;

import com.armario.domain.port.outbound.ProdutoRepositoryPort;
import com.armario.domain.service.ProdutoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ProdutoService produtoService(ProdutoRepositoryPort repositoryPort) {
        return new ProdutoService(repositoryPort);
    }
}
