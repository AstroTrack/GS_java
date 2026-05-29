package br.com.fiap.AstroTrack.service;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class HateoasMenuService {

	public RepresentationModel<?> crudMenu(String recurso, String basePath, String idTemplate) {
		RepresentationModel<?> menu = new RepresentationModel<>();
		menu.add(link(basePath + "/hateoas", "menu-" + recurso, "GET"));
		menu.add(link(basePath, "listar-" + recurso, "GET"));
		menu.add(link(basePath + "/{" + idTemplate + "}", "buscar-" + recurso, "GET"));
		menu.add(link(basePath, "criar-" + recurso, "POST"));
		menu.add(link(basePath + "/{" + idTemplate + "}", "atualizar-" + recurso, "PUT"));
		menu.add(link(basePath + "/{" + idTemplate + "}", "remover-" + recurso, "DELETE"));
		return menu;
	}

	public RepresentationModel<?> viagensMenu() {
		RepresentationModel<?> menu = crudMenu("viagens", "/viagens", "id");
		menu.add(link("/viagens/status{?status}", "filtrar-viagens-por-status", "GET"));
		return menu;
	}

	public RepresentationModel<?> checkpointsMenu() {
		RepresentationModel<?> menu = crudMenu("checkpoints", "/checkpoints", "id");
		menu.add(link("/checkpoints/viagem/{idViagem}", "listar-checkpoints-por-viagem", "GET"));
		return menu;
	}

	public RepresentationModel<?> apiMenu() {
		RepresentationModel<?> menu = new RepresentationModel<>();
		menu.add(link("/hateoas", "menu-geral", "GET"));
		menu.add(link("/auth/register", "registrar-usuario", "POST"));
		menu.add(link("/auth/login", "login", "POST"));
		menu.add(link("/clientes/hateoas", "menu-clientes", "GET"));
		menu.add(link("/motoristas/hateoas", "menu-motoristas", "GET"));
		menu.add(link("/veiculos/hateoas", "menu-veiculos", "GET"));
		menu.add(link("/viagens/hateoas", "menu-viagens", "GET"));
		menu.add(link("/checkpoints/hateoas", "menu-checkpoints", "GET"));
		menu.add(link("/clientes", "listar-clientes", "GET"));
		menu.add(link("/clientes/{id}", "buscar-cliente", "GET"));
		menu.add(link("/clientes", "criar-cliente", "POST"));
		menu.add(link("/clientes/{id}", "atualizar-cliente", "PUT"));
		menu.add(link("/clientes/{id}", "remover-cliente", "DELETE"));
		menu.add(link("/motoristas", "listar-motoristas", "GET"));
		menu.add(link("/motoristas/{id}", "buscar-motorista", "GET"));
		menu.add(link("/motoristas", "criar-motorista", "POST"));
		menu.add(link("/motoristas/{id}", "atualizar-motorista", "PUT"));
		menu.add(link("/motoristas/{id}", "remover-motorista", "DELETE"));
		menu.add(link("/veiculos", "listar-veiculos", "GET"));
		menu.add(link("/veiculos/{id}", "buscar-veiculo", "GET"));
		menu.add(link("/veiculos", "criar-veiculo", "POST"));
		menu.add(link("/veiculos/{id}", "atualizar-veiculo", "PUT"));
		menu.add(link("/veiculos/{id}", "remover-veiculo", "DELETE"));
		menu.add(link("/viagens", "listar-viagens", "GET"));
		menu.add(link("/viagens/status{?status}", "filtrar-viagens-por-status", "GET"));
		menu.add(link("/viagens/{id}", "buscar-viagem", "GET"));
		menu.add(link("/viagens", "criar-viagem", "POST"));
		menu.add(link("/viagens/{id}", "atualizar-viagem", "PUT"));
		menu.add(link("/viagens/{id}", "remover-viagem", "DELETE"));
		menu.add(link("/checkpoints", "listar-checkpoints", "GET"));
		menu.add(link("/checkpoints/viagem/{idViagem}", "listar-checkpoints-por-viagem", "GET"));
		menu.add(link("/checkpoints/{id}", "buscar-checkpoint", "GET"));
		menu.add(link("/checkpoints", "criar-checkpoint", "POST"));
		menu.add(link("/checkpoints/{id}", "atualizar-checkpoint", "PUT"));
		menu.add(link("/checkpoints/{id}", "remover-checkpoint", "DELETE"));
		return menu;
	}

	private Link link(String path, String rel, String httpMethod) {
		String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		return Link.of(baseUrl + path).withRel(rel).withType(httpMethod);
	}
}
