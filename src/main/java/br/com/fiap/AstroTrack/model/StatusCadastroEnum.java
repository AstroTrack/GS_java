package br.com.fiap.AstroTrack.model;

public enum StatusCadastroEnum {
	ATIVO("Ativo"),
	INATIVO("Inativo"),
	BLOQUEADO("Bloqueado");

	private String descricao;

	private StatusCadastroEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
