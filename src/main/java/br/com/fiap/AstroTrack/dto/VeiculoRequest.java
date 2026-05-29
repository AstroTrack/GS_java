package br.com.fiap.AstroTrack.dto;

import br.com.fiap.AstroTrack.model.StatusVeiculoEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VeiculoRequest(
		@NotBlank(message = "A placa é obrigatória")
		@Pattern(regexp = "[A-Z]{3}[0-9][A-Z0-9][0-9]{2}", message = "A placa deve seguir o padrão Mercosul ou antigo sem hífen")
		String placa,

		@NotBlank(message = "O modelo é obrigatório")
		@Size(max = 80, message = "O modelo deve possuir no máximo 80 caracteres")
		String modelo,

		@NotBlank(message = "A marca é obrigatória")
		@Size(max = 60, message = "A marca deve possuir no máximo 60 caracteres")
		String marca,

		@NotNull(message = "O ano é obrigatório")
		@Min(value = 1980, message = "O ano mínimo permitido é 1980")
		@Max(value = 2100, message = "O ano máximo permitido é 2100")
		Integer ano,

		@NotNull(message = "O status é obrigatório")
		StatusVeiculoEnum status) {
}
