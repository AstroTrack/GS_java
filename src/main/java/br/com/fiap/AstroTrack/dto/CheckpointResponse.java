package br.com.fiap.AstroTrack.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CheckpointResponse(
		Long idCheckpoint,
		Long idViagem,
		BigDecimal latitude,
		BigDecimal longitude,
		LocalDateTime dataRegistro,
		Boolean botaoPanico,
		Boolean portaAberta) {
}
