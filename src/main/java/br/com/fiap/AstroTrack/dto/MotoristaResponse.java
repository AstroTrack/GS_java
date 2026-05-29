package br.com.fiap.AstroTrack.dto;

import br.com.fiap.AstroTrack.model.StatusCadastroEnum;

public record MotoristaResponse(
		Long idMotorista,
		String nome,
		String cpf,
		String cnh,
		String telefone,
		StatusCadastroEnum status) {
}
