package com.armario.domain.port.outbound;

public interface TokenProviderPort {
    String gerarToken(String subject);
}
