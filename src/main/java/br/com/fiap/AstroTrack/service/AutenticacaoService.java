package br.com.fiap.AstroTrack.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.AstroTrack.dto.AuthRequest;
import br.com.fiap.AstroTrack.dto.AuthResponse;
import br.com.fiap.AstroTrack.dto.RegisterRequest;
import br.com.fiap.AstroTrack.dto.UsuarioResponse;
import br.com.fiap.AstroTrack.exception.RegraNegocioException;
import br.com.fiap.AstroTrack.model.StatusCadastroEnum;
import br.com.fiap.AstroTrack.model.UsuarioSistema;
import br.com.fiap.AstroTrack.model.UsuarioSistemaId;
import br.com.fiap.AstroTrack.repository.UsuarioSistemaRepository;
import br.com.fiap.AstroTrack.security.JWTUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutenticacaoService {

	private final UsuarioSistemaRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager manager;
	private final JWTUtil jwtUtil;

	@Transactional
	public AuthResponse registrar(RegisterRequest request) {
		String usuarioNormalizado = request.usuario().trim().toLowerCase();
		String emailNormalizado = request.email().trim().toLowerCase();

		if (usuarioRepository.existsByIdEmail(emailNormalizado)) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este e-mail");
		}

		UsuarioSistema usuario = new UsuarioSistema();
		usuario.setId(new UsuarioSistemaId(usuarioNormalizado, emailNormalizado));
		usuario.setSenha(passwordEncoder.encode(request.senha()));
		usuario.setStatus(StatusCadastroEnum.ATIVO);

		usuarioRepository.save(usuario);

		return new AuthResponse(jwtUtil.gerarToken(usuario.getId().getEmail()), "Bearer", toResponse(usuario));
	}

	public AuthResponse logar(AuthRequest request) {
		String emailNormalizado = request.email().trim().toLowerCase();
		var autenticacao = new UsernamePasswordAuthenticationToken(emailNormalizado, request.senha());
		manager.authenticate(autenticacao);

		UsuarioSistema usuario = usuarioRepository
				.findFirstByIdEmailAndStatus(emailNormalizado, StatusCadastroEnum.ATIVO)
				.orElseThrow(() -> new RegraNegocioException("Credenciais inválidas"));

		return new AuthResponse(jwtUtil.gerarToken(usuario.getId().getEmail()), "Bearer", toResponse(usuario));
	}

	private UsuarioResponse toResponse(UsuarioSistema usuario) {
		return new UsuarioResponse(
				usuario.getId().getUsuario(),
				usuario.getId().getEmail(),
				usuario.getStatus(),
				usuario.getDataCriacao());
	}
}
