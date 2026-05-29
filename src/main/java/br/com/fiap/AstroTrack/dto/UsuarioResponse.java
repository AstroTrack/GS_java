package br.com.fiap.AstroTrack.dto;

import java.time.LocalDateTime;

import br.com.fiap.AstroTrack.model.StatusCadastroEnum;

public record UsuarioResponse(
		String usuario,
		String email,
		StatusCadastroEnum status,
		LocalDateTime dataCriacao) {
}
