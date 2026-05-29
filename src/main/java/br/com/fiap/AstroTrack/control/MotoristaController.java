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

import br.com.fiap.AstroTrack.dto.MotoristaRequest;
import br.com.fiap.AstroTrack.dto.MotoristaResponse;
import br.com.fiap.AstroTrack.service.HateoasMenuService;
import br.com.fiap.AstroTrack.service.MotoristaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/motoristas")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class MotoristaController {

	private final MotoristaService motoristaService;
	private final HateoasMenuService hateoasMenuService;

	@Operation(summary = "Menu HATEOAS de motoristas", description = "Retorna links para listar, buscar, criar, atualizar e remover motoristas")
	@GetMapping("/hateoas")
	public ResponseEntity<RepresentationModel<?>> hateoas() {
		return ResponseEntity.ok(hateoasMenuService.crudMenu("motoristas", "/motoristas", "id"));
	}

	@Operation(summary = "Listar motoristas", description = "Retorna todos os motoristas cadastrados")
	@GetMapping
	public ResponseEntity<List<MotoristaResponse>> listarTodos() {
		return ResponseEntity.ok(motoristaService.listarTodos());
	}

	@Operation(summary = "Buscar motorista por ID", description = "Retorna um motorista pelo identificador")
	@GetMapping("/{id}")
	public ResponseEntity<MotoristaResponse> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(motoristaService.buscarPorId(id));
	}

	@Operation(summary = "Inserir motorista", description = "Cria um novo motorista")
	@PostMapping
	public ResponseEntity<MotoristaResponse> inserir(@RequestBody @Valid MotoristaRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(motoristaService.inserir(request));
	}

	@Operation(summary = "Atualizar motorista", description = "Atualiza um motorista existente")
	@PutMapping("/{id}")
	public ResponseEntity<MotoristaResponse> atualizar(@PathVariable Long id,
			@RequestBody @Valid MotoristaRequest request) {
		return ResponseEntity.ok(motoristaService.atualizar(id, request));
	}

	@Operation(summary = "Remover motorista", description = "Remove um motorista pelo identificador")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		motoristaService.remover(id);
		return ResponseEntity.noContent().build();
	}
}
