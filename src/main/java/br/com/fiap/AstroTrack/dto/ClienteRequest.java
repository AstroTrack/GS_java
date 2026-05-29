package br.com.fiap.AstroTrack.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import br.com.fiap.AstroTrack.model.StatusCadastroEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequest(
		@NotBlank(message = "O nome é obrigatório")
		@Size(min = 2, max = 100, message = "O nome deve possuir entre 2 e 100 caracteres")
		String nome,

		@NotBlank(message = "O CNPJ é obrigatório")
		@CNPJ(message = "O CNPJ informado é inválido")
		String cnpj,

		@NotBlank(message = "O e-mail é obrigatório")
		@Email(message = "O e-mail informado é inválido")
		String email,

		@NotBlank(message = "O telefone é obrigatório")
		@Pattern(regexp = "\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}", message = "O telefone deve estar em um formato válido")
		String telefone,

		@NotNull(message = "O status é obrigatório")
		StatusCadastroEnum status) {
}
