package com.armario.adapter.inbound.dto;

public record LoginRequest(
        String login,
        String senha
) {
}
