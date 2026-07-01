package com.armario.adapter.outbound.persistence;

import com.armario.adapter.outbound.persistence.entity.ProdutoEntity;
import com.armario.adapter.outbound.persistence.mapper.ProdutoMapper;
import com.armario.adapter.outbound.persistence.repository.ProdutoJpaRepository;
import com.armario.domain.model.Produto;
import com.armario.domain.port.outbound.ProdutoRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProdutoRepositoryAdapter implements ProdutoRepositoryPort {

    private final ProdutoJpaRepository jpaRepository;

    public ProdutoRepositoryAdapter(ProdutoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Produto salvar(Produto produto) {
        ProdutoEntity entity = ProdutoMapper.toEntity(produto);
        ProdutoEntity saved = jpaRepository.save(entity);
        return ProdutoMapper.toDomain(saved);
    }

    @Override
    public Optional<Produto> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(ProdutoMapper::toDomain);
    }

    @Override
    public List<Produto> buscarTodos() {
        return jpaRepository.findAll().stream()
                .map(ProdutoMapper::toDomain)
                .toList();
    }
}
