package br.com.fiap.AstroTrack.control;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.AstroTrack.dto.VeiculoRequest;
import br.com.fiap.AstroTrack.dto.VeiculoResponse;
import br.com.fiap.AstroTrack.service.HateoasMenuService;
import br.com.fiap.AstroTrack.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class VeiculoController {

	private final VeiculoService veiculoService;
	private final HateoasMenuService hateoasMenuService;

	@Operation(summary = "Menu HATEOAS de veículos", description = "Retorna links para listar, buscar, criar, atualizar e remover veículos")
	@GetMapping("/hateoas")
	public ResponseEntity<RepresentationModel<?>> hateoas() {
		return ResponseEntity.ok(hateoasMenuService.crudMenu("veiculos", "/veiculos", "id"));
	}

	@Operation(summary = "Listar veículos", description = "Retorna todos os veículos cadastrados")
	@GetMapping
	public ResponseEntity<List<VeiculoResponse>> listarTodos() {
		return ResponseEntity.ok(veiculoService.listarTodos());
	}

	@Operation(summary = "Buscar veículo por ID", description = "Retorna um veículo pelo identificador")
	@GetMapping("/{id}")
	public ResponseEntity<VeiculoResponse> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(veiculoService.buscarPorId(id));
	}

	@Operation(summary = "Inserir veículo", description = "Cria um novo veículo")
	@PostMapping
	public ResponseEntity<VeiculoResponse> inserir(@RequestBody @Valid VeiculoRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(veiculoService.inserir(request));
	}

	@Operation(summary = "Atualizar veículo", description = "Atualiza um veículo existente")
	@PutMapping("/{id}")
	public ResponseEntity<VeiculoResponse> atualizar(@PathVariable Long id,
			@RequestBody @Valid VeiculoRequest request) {
		return ResponseEntity.ok(veiculoService.atualizar(id, request));
	}

	@Operation(summary = "Remover veículo", description = "Remove um veículo pelo identificador")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		veiculoService.remover(id);
		return ResponseEntity.noContent().build();
	}
}
