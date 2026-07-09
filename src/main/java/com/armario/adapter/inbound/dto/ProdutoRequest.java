package com.armario.adapter.inbound.dto;

import java.time.LocalDate;

public record ProdutoRequest(
        String nome,
        Double peso,
        String tipo,
        LocalDate dataFabricacao,
        LocalDate dataValidade
) {
}
