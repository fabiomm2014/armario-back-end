package com.armario.adapter.outbound.persistence.repository;

import com.armario.adapter.outbound.persistence.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProdutoJpaRepository extends JpaRepository<ProdutoEntity, UUID> {
}
