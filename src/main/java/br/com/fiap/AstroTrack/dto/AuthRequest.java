package br.com.fiap.AstroTrack.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
		@NotBlank(message = "O e-mail é obrigatório")
		@Email(message = "O e-mail informado é inválido")
		@Size(max = 120, message = "O e-mail deve possuir no máximo 120 caracteres")
		String email,

		@NotBlank(message = "A senha é obrigatória")
		String senha) {
}
