package br.com.fiap.AstroTrack.dto;

import br.com.fiap.AstroTrack.model.StatusVeiculoEnum;

public record VeiculoResponse(
		Long idVeiculo,
		String placa,
		String modelo,
		String marca,
		Integer ano,
		StatusVeiculoEnum status) {
}
