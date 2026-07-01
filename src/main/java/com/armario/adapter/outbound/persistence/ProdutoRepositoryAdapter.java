package com.armario.adapter.outbound.persistence;

import com.armario.adapter.outbound.persistence.entity.ProdutoEntity;
import com.armario.adapter.outbound.persistence.mapper.ProdutoEntityMapper;
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
    private final ProdutoEntityMapper mapper;

    public ProdutoRepositoryAdapter(ProdutoJpaRepository jpaRepository, ProdutoEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Produto salvar(Produto produto) {
        ProdutoEntity entity = mapper.toEntity(produto);
        ProdutoEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Produto> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Produto> buscarTodos() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deletar(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existePorId(UUID id) {
        return jpaRepository.existsById(id);
    }
}
