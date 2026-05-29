package br.com.fiap.AstroTrack.dto;

import br.com.fiap.AstroTrack.model.StatusCadastroEnum;

public record ClienteResponse(
		Long idCliente,
		String nome,
		String cnpj,
		String email,
		String telefone,
		StatusCadastroEnum status) {
}
