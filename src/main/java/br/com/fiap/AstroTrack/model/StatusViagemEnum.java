package br.com.fiap.AstroTrack.model;

public enum StatusViagemEnum {
	AGENDADA("Agendada"),
	EM_ANDAMENTO("Em andamento"),
	FINALIZADA("Finalizada"),
	CANCELADA("Cancelada");

	private String descricao;

	private StatusViagemEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
