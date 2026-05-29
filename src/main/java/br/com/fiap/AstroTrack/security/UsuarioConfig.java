package br.com.fiap.AstroTrack.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.AstroTrack.model.StatusCadastroEnum;
import br.com.fiap.AstroTrack.model.UsuarioSistema;
import br.com.fiap.AstroTrack.repository.UsuarioSistemaRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class UsuarioConfig {

	private final UsuarioSistemaRepository usuarioRepository;

	@Bean
	public UserDetailsService gerarUsuario() {
		return username -> {
			UsuarioSistema usuario = usuarioRepository
					.findFirstByIdEmailAndStatus(username, StatusCadastroEnum.ATIVO)
					.orElseThrow(() -> new UsernameNotFoundException("Usuário não localizado ou inativo"));

			return User.builder()
					.username(usuario.getId().getEmail())
					.password(usuario.getSenha())
					.roles("USER")
					.build();
		};
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
