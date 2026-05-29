package br.com.fiap.AstroTrack.model;

public enum StatusVeiculoEnum {
	DISPONIVEL("Disponivel"),
	EM_MANUTENCAO("Em manutencao"),
	EM_VIAGEM("Em viagem"),
	INATIVO("Inativo");

	private String descricao;

	private StatusVeiculoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
