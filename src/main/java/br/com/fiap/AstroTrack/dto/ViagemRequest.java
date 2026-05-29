package br.com.fiap.AstroTrack.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.fiap.AstroTrack.model.StatusViagemEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ViagemRequest(
		@NotNull(message = "O cliente é obrigatório")
		Long idCliente,

		@NotNull(message = "O motorista é obrigatório")
		Long idMotorista,

		@NotNull(message = "O veículo é obrigatório")
		Long idVeiculo,

		@NotBlank(message = "A origem é obrigatória")
		@Size(max = 120, message = "A origem deve possuir no máximo 120 caracteres")
		String origem,

		@NotBlank(message = "O destino é obrigatório")
		@Size(max = 120, message = "O destino deve possuir no máximo 120 caracteres")
		String destino,

		@NotNull(message = "A data de início é obrigatória")
		LocalDateTime dataInicio,

		LocalDateTime dataFim,

		@NotNull(message = "O status é obrigatório")
		StatusViagemEnum status,

		@NotNull(message = "A quilometragem total é obrigatória")
		@DecimalMin(value = "0.0", message = "A quilometragem total não pode ser negativa")
		BigDecimal quilometragemTotal) {
}
