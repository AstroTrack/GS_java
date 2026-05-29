package br.com.fiap.AstroTrack.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AT_CHECKPOINTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Checkpoint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_checkpoint")
	private Long idCheckpoint;

	@NotNull(message = "A viagem é obrigatória")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_viagem", nullable = false)
	private Viagem viagem;

	@Valid
	@NotNull(message = "A coordenada é obrigatória")
	@Embedded
	private Coordenada coordenada;

	@NotNull(message = "A data de registro é obrigatória")
	@PastOrPresent(message = "A data de registro não pode ser futura")
	@Column(name = "data_registro", nullable = false)
	private LocalDateTime dataRegistro;

	@NotNull(message = "A informação do botão de pânico é obrigatória")
	@Column(name = "botao_panico", nullable = false)
	private Boolean botaoPanico;

	@NotNull(message = "A informação de porta aberta é obrigatória")
	@Column(name = "porta_aberta", nullable = false)
	private Boolean portaAberta;
}
