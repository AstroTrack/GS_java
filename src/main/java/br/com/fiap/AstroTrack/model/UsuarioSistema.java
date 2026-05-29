package br.com.fiap.AstroTrack.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AT_USUARIOS_SISTEMA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSistema {

	@Valid
	@EmbeddedId
	private UsuarioSistemaId id;

	@NotBlank(message = "A senha é obrigatória")
	@Column(name = "senha", nullable = false)
	private String senha;

	@NotNull(message = "O status é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private StatusCadastroEnum status;

	@NotNull(message = "A data de criação é obrigatória")
	@Column(name = "data_criacao", nullable = false)
	private LocalDateTime dataCriacao;

	@PrePersist
	public void prePersist() {
		if (dataCriacao == null) {
			dataCriacao = LocalDateTime.now();
		}
	}
}
