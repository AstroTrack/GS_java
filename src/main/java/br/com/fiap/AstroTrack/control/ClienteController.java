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

import br.com.fiap.AstroTrack.dto.ClienteRequest;
import br.com.fiap.AstroTrack.dto.ClienteResponse;
import br.com.fiap.AstroTrack.service.ClienteService;
import br.com.fiap.AstroTrack.service.HateoasMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

	private final ClienteService clienteService;
	private final HateoasMenuService hateoasMenuService;

	@Operation(summary = "Menu HATEOAS de clientes", description = "Retorna links para listar, buscar, criar, atualizar e remover clientes")
	@GetMapping("/hateoas")
	public ResponseEntity<RepresentationModel<?>> hateoas() {
		return ResponseEntity.ok(hateoasMenuService.crudMenu("clientes", "/clientes", "id"));
	}

	@Operation(summary = "Listar clientes", description = "Retorna todos os clientes cadastrados")
	@GetMapping
	public ResponseEntity<List<ClienteResponse>> listarTodos() {
		return ResponseEntity.ok(clienteService.listarTodos());
	}

	@Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico pelo identificador")
	@GetMapping("/{id}")
	public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(clienteService.buscarPorId(id));
	}

	@Operation(summary = "Inserir cliente", description = "Cria um novo cliente")
	@PostMapping
	public ResponseEntity<ClienteResponse> inserir(@RequestBody @Valid ClienteRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.inserir(request));
	}

	@Operation(summary = "Atualizar cliente", description = "Atualiza todos os dados de um cliente existente")
	@PutMapping("/{id}")
	public ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id,
			@RequestBody @Valid ClienteRequest request) {
		return ResponseEntity.ok(clienteService.atualizar(id, request));
	}

	@Operation(summary = "Remover cliente", description = "Remove um cliente pelo identificador")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		clienteService.remover(id);
		return ResponseEntity.noContent().build();
	}
}
