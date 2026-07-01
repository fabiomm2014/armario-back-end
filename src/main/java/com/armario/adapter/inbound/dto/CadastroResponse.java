package com.armario.adapter.inbound.dto;

import java.util.UUID;

public record CadastroResponse(
        UUID id,
        String login
) {
}
