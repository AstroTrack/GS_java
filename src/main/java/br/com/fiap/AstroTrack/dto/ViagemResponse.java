package br.com.fiap.AstroTrack.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.fiap.AstroTrack.model.StatusViagemEnum;

public record ViagemResponse(
		Long idViagem,
		Long idCliente,
		String nomeCliente,
		Long idMotorista,
		String nomeMotorista,
		Long idVeiculo,
		String placaVeiculo,
		String origem,
		String destino,
		LocalDateTime dataInicio,
		LocalDateTime dataFim,
		StatusViagemEnum status,
		BigDecimal quilometragemTotal) {
}
