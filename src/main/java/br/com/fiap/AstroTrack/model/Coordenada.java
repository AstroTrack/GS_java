package br.com.fiap.AstroTrack.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordenada {

	@NotNull(message = "A latitude é obrigatória")
	@DecimalMin(value = "-90.0", message = "A latitude mínima é -90")
	@DecimalMax(value = "90.0", message = "A latitude máxima é 90")
	@Column(name = "latitude", nullable = false, precision = 10, scale = 7)
	private BigDecimal latitude;

	@NotNull(message = "A longitude é obrigatória")
	@DecimalMin(value = "-180.0", message = "A longitude mínima é -180")
	@DecimalMax(value = "180.0", message = "A longitude máxima é 180")
	@Column(name = "longitude", nullable = false, precision = 10, scale = 7)
	private BigDecimal longitude;
}
