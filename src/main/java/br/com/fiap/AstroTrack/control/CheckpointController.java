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

import br.com.fiap.AstroTrack.dto.CheckpointRequest;
import br.com.fiap.AstroTrack.dto.CheckpointResponse;
import br.com.fiap.AstroTrack.service.CheckpointService;
import br.com.fiap.AstroTrack.service.HateoasMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/checkpoints")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CheckpointController {

	private final CheckpointService checkpointService;
	private final HateoasMenuService hateoasMenuService;

	@Operation(summary = "Menu HATEOAS de checkpoints", description = "Retorna links para listar, listar por viagem, buscar, criar, atualizar e remover checkpoints")
	@GetMapping("/hateoas")
	public ResponseEntity<RepresentationModel<?>> hateoas() {
		return ResponseEntity.ok(hateoasMenuService.checkpointsMenu());
	}

	@Operation(summary = "Listar checkpoints", description = "Retorna todos os checkpoints cadastrados")
	@GetMapping
	public ResponseEntity<List<CheckpointResponse>> listarTodos() {
		return ResponseEntity.ok(checkpointService.listarTodos());
	}

	@Operation(summary = "Listar checkpoints por viagem", description = "Retorna o rastro manual de uma viagem")
	@GetMapping("/viagem/{idViagem}")
	public ResponseEntity<List<CheckpointResponse>> listarPorViagem(@PathVariable Long idViagem) {
		return ResponseEntity.ok(checkpointService.listarPorViagem(idViagem));
	}

	@Operation(summary = "Buscar checkpoint por ID", description = "Retorna um checkpoint pelo identificador")
	@GetMapping("/{id}")
	public ResponseEntity<CheckpointResponse> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(checkpointService.buscarPorId(id));
	}

	@Operation(summary = "Inserir checkpoint", description = "Registra uma posição de rastreio via satélite")
	@PostMapping
	public ResponseEntity<CheckpointResponse> inserir(@RequestBody @Valid CheckpointRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(checkpointService.inserir(request));
	}

	@Operation(summary = "Atualizar checkpoint", description = "Atualiza um checkpoint existente")
	@PutMapping("/{id}")
	public ResponseEntity<CheckpointResponse> atualizar(@PathVariable Long id,
			@RequestBody @Valid CheckpointRequest request) {
		return ResponseEntity.ok(checkpointService.atualizar(id, request));
	}

	@Operation(summary = "Remover checkpoint", description = "Remove um checkpoint pelo identificador")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		checkpointService.remover(id);
		return ResponseEntity.noContent().build();
	}
}
