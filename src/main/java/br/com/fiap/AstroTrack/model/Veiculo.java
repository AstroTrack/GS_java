package br.com.fiap.AstroTrack.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AT_VEICULOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_veiculo")
	private Long idVeiculo;

	@NotBlank(message = "A placa é obrigatória")
	@Pattern(regexp = "[A-Z]{3}[0-9][A-Z0-9][0-9]{2}", message = "A placa deve seguir o padrão Mercosul ou antigo sem hífen")
	@Column(name = "placa", nullable = false, unique = true, length = 7)
	private String placa;

	@NotBlank(message = "O modelo é obrigatório")
	@Size(max = 80, message = "O modelo deve possuir no máximo 80 caracteres")
	@Column(name = "modelo", nullable = false, length = 80)
	private String modelo;

	@NotBlank(message = "A marca é obrigatória")
	@Size(max = 60, message = "A marca deve possuir no máximo 60 caracteres")
	@Column(name = "marca", nullable = false, length = 60)
	private String marca;

	@NotNull(message = "O ano é obrigatório")
	@Min(value = 1980, message = "O ano mínimo permitido é 1980")
	@Max(value = 2100, message = "O ano máximo permitido é 2100")
	@Column(name = "ano", nullable = false)
	private Integer ano;

	@NotNull(message = "O status é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private StatusVeiculoEnum status;
}
