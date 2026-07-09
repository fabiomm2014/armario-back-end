package com.armario.adapter.outbound.persistence;

import com.armario.adapter.outbound.persistence.mapper.UsuarioEntityMapper;
import com.armario.adapter.outbound.persistence.repository.UsuarioJpaRepository;
import com.armario.domain.model.Usuario;
import com.armario.domain.port.outbound.UsuarioRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;
    private final UsuarioEntityMapper mapper;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository jpaRepository, UsuarioEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(usuario)));
    }

    @Override
    public Optional<Usuario> buscarPorLogin(String login) {
        return jpaRepository.findByLogin(login).map(mapper::toDomain);
    }

    @Override
    public boolean existePorLogin(String login) {
        return jpaRepository.existsByLogin(login);
    }
}
