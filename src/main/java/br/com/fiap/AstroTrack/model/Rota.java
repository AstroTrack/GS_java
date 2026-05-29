package br.com.fiap.AstroTrack.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rota {

	@NotBlank(message = "A origem é obrigatória")
	@Size(max = 120, message = "A origem deve possuir no máximo 120 caracteres")
	@Column(name = "origem", nullable = false, length = 120)
	private String origem;

	@NotBlank(message = "O destino é obrigatório")
	@Size(max = 120, message = "O destino deve possuir no máximo 120 caracteres")
	@Column(name = "destino", nullable = false, length = 120)
	private String destino;
}
