package br.com.fiap.porquinho.infrastructure.config;

import lombok.Builder;

@Builder
public record JwtUserData(
        Long userId,
        String email) {

}
