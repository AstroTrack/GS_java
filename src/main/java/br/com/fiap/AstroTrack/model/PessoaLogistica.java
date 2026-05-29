package br.com.fiap.AstroTrack.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class PessoaLogistica {

	@NotBlank(message = "O nome é obrigatório")
	@Size(min = 2, max = 100, message = "O nome deve possuir entre 2 e 100 caracteres")
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;

	@NotBlank(message = "O telefone é obrigatório")
	@Pattern(regexp = "\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}", message = "O telefone deve estar em um formato válido")
	@Column(name = "telefone", nullable = false, length = 20)
	private String telefone;

	@NotNull(message = "O status é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private StatusCadastroEnum status;
}
