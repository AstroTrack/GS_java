package br.com.fiap.AstroTrack.control;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.AstroTrack.dto.AuthRequest;
import br.com.fiap.AstroTrack.dto.AuthResponse;
import br.com.fiap.AstroTrack.dto.RegisterRequest;
import br.com.fiap.AstroTrack.service.AutenticacaoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

	private final AutenticacaoService autenticacaoService;

	@Operation(summary = "Registrar usuário", description = "Cria um usuário do sistema e retorna um token JWT para autenticação nos endpoints protegidos")
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> registrar(@RequestBody @Valid RegisterRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(autenticacaoService.registrar(request));
	}

	@Operation(summary = "Login", description = "Autentica o usuário e retorna um token JWT")
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> logar(@RequestBody @Valid AuthRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(autenticacaoService.logar(request));
	}
}
