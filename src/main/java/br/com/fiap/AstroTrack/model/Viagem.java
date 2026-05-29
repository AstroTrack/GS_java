package br.com.fiap.AstroTrack.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AT_VIAGENS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Viagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_viagem")
	private Long idViagem;

	@NotNull(message = "O cliente é obrigatório")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@NotNull(message = "O motorista é obrigatório")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_motorista", nullable = false)
	private Motorista motorista;

	@NotNull(message = "O veículo é obrigatório")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_veiculo", nullable = false)
	private Veiculo veiculo;

	@Valid
	@NotNull(message = "A rota é obrigatória")
	@Embedded
	private Rota rota;

	@NotNull(message = "A data de início é obrigatória")
	@Column(name = "data_inicio", nullable = false)
	private LocalDateTime dataInicio;

	@Column(name = "data_fim")
	private LocalDateTime dataFim;

	@NotNull(message = "O status é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private StatusViagemEnum status;

	@NotNull(message = "A quilometragem total é obrigatória")
	@DecimalMin(value = "0.0", message = "A quilometragem total não pode ser negativa")
	@Column(name = "quilometragem_total", nullable = false, precision = 12, scale = 2)
	private BigDecimal quilometragemTotal;

	@OneToMany(mappedBy = "viagem", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Checkpoint> checkpoints = new ArrayList<>();
}
