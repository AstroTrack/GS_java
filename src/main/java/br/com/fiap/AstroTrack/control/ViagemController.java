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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.AstroTrack.dto.ViagemRequest;
import br.com.fiap.AstroTrack.dto.ViagemResponse;
import br.com.fiap.AstroTrack.model.StatusViagemEnum;
import br.com.fiap.AstroTrack.service.HateoasMenuService;
import br.com.fiap.AstroTrack.service.ViagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/viagens")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ViagemController {

	private final ViagemService viagemService;
	private final HateoasMenuService hateoasMenuService;

	@Operation(summary = "Menu HATEOAS de viagens", description = "Retorna links para listar, filtrar, buscar, criar, atualizar e remover viagens")
	@GetMapping("/hateoas")
	public ResponseEntity<RepresentationModel<?>> hateoas() {
		return ResponseEntity.ok(hateoasMenuService.viagensMenu());
	}

	@Operation(summary = "Listar viagens", description = "Retorna todas as viagens cadastradas")
	@GetMapping
	public ResponseEntity<List<ViagemResponse>> listarTodos() {
		return ResponseEntity.ok(viagemService.listarTodos());
	}

	@Operation(summary = "Listar viagens por status", description = "Retorna viagens filtradas por status")
	@GetMapping("/status")
	public ResponseEntity<List<ViagemResponse>> listarPorStatus(@RequestParam StatusViagemEnum status) {
		return ResponseEntity.ok(viagemService.listarPorStatus(status));
	}

	@Operation(summary = "Buscar viagem por ID", description = "Retorna uma viagem pelo identificador")
	@GetMapping("/{id}")
	public ResponseEntity<ViagemResponse> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(viagemService.buscarPorId(id));
	}

	@Operation(summary = "Inserir viagem", description = "Cria uma nova viagem logística")
	@PostMapping
	public ResponseEntity<ViagemResponse> inserir(@RequestBody @Valid ViagemRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(viagemService.inserir(request));
	}

	@Operation(summary = "Atualizar viagem", description = "Atualiza uma viagem existente")
	@PutMapping("/{id}")
	public ResponseEntity<ViagemResponse> atualizar(@PathVariable Long id,
			@RequestBody @Valid ViagemRequest request) {
		return ResponseEntity.ok(viagemService.atualizar(id, request));
	}

	@Operation(summary = "Remover viagem", description = "Remove uma viagem que não esteja em andamento")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		viagemService.remover(id);
		return ResponseEntity.noContent().build();
	}
}
