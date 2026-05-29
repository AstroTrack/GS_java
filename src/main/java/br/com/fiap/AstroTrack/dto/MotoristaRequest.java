package br.com.fiap.AstroTrack.dto;

import org.hibernate.validator.constraints.br.CPF;

import br.com.fiap.AstroTrack.model.StatusCadastroEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MotoristaRequest(
		@NotBlank(message = "O nome é obrigatório")
		@Size(min = 2, max = 100, message = "O nome deve possuir entre 2 e 100 caracteres")
		String nome,

		@NotBlank(message = "O CPF é obrigatório")
		@CPF(message = "O CPF informado é inválido")
		String cpf,

		@NotBlank(message = "A CNH é obrigatória")
		@Pattern(regexp = "\\d{11}", message = "A CNH deve possuir 11 dígitos")
		String cnh,

		@NotBlank(message = "O telefone é obrigatório")
		@Pattern(regexp = "\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}", message = "O telefone deve estar em um formato válido")
		String telefone,

		@NotNull(message = "O status é obrigatório")
		StatusCadastroEnum status) {
}
