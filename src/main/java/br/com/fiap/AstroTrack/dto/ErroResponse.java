package br.com.fiap.AstroTrack.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ErroResponse(
		LocalDateTime timestamp,
		Integer status,
		String erro,
		String mensagem,
		String caminho,
		Map<String, String> campos) {
}
