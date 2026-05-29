package br.com.fiap.AstroTrack.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
		@NotBlank(message = "O usuário é obrigatório")
		@Size(max = 60, message = "O usuário deve possuir no máximo 60 caracteres")
		String usuario,

		@NotBlank(message = "O e-mail é obrigatório")
		@Email(message = "O e-mail informado é inválido")
		@Size(max = 120, message = "O e-mail deve possuir no máximo 120 caracteres")
		String email,

		@NotBlank(message = "A senha é obrigatória")
		@Size(min = 6, max = 80, message = "A senha deve possuir entre 6 e 80 caracteres")
		String senha) {
}
