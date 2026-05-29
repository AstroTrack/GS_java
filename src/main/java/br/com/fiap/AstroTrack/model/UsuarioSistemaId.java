package br.com.fiap.AstroTrack.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSistemaId implements Serializable {

	@NotBlank(message = "O usuário é obrigatório")
	@Column(name = "usuario", nullable = false, length = 60)
	private String usuario;

	@NotBlank(message = "O e-mail é obrigatório")
	@Email(message = "O e-mail informado é inválido")
	@Size(max = 120, message = "O e-mail deve possuir no máximo 120 caracteres")
	@Column(name = "email", nullable = false, length = 120)
	private String email;
}
