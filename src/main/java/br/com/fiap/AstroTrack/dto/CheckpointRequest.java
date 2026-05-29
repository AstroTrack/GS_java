package br.com.fiap.AstroTrack.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record CheckpointRequest(
		@NotNull(message = "A viagem é obrigatória")
		Long idViagem,

		@NotNull(message = "A latitude é obrigatória")
		@DecimalMin(value = "-90.0", message = "A latitude mínima é -90")
		@DecimalMax(value = "90.0", message = "A latitude máxima é 90")
		BigDecimal latitude,

		@NotNull(message = "A longitude é obrigatória")
		@DecimalMin(value = "-180.0", message = "A longitude mínima é -180")
		@DecimalMax(value = "180.0", message = "A longitude máxima é 180")
		BigDecimal longitude,

		@NotNull(message = "A data de registro é obrigatória")
		@PastOrPresent(message = "A data de registro não pode ser futura")
		LocalDateTime dataRegistro,

		@NotNull(message = "A informação do botão de pânico é obrigatória")
		Boolean botaoPanico,

		@NotNull(message = "A informação de porta aberta é obrigatória")
		Boolean portaAberta) {
}
