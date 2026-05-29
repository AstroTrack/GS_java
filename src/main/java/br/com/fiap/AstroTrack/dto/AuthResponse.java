package br.com.fiap.AstroTrack.dto;

public record AuthResponse(
		String token,
		String type,
		UsuarioResponse usuario) {
}
