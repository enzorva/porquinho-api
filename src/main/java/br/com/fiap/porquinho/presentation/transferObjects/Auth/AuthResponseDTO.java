package br.com.fiap.porquinho.presentation.transferObjects.Auth;

public record AuthResponseDTO(
        String token,
        String refreshToken) {

}
