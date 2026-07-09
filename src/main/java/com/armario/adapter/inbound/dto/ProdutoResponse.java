package com.armario.adapter.inbound.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ProdutoResponse(
        UUID id,
        String nome,
        Double peso,
        String tipo,
        LocalDate dataFabricacao,
        LocalDate dataValidade
) {
}
