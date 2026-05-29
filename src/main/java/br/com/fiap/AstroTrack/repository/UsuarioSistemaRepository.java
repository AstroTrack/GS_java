package br.com.fiap.AstroTrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.AstroTrack.model.StatusCadastroEnum;
import br.com.fiap.AstroTrack.model.UsuarioSistema;
import br.com.fiap.AstroTrack.model.UsuarioSistemaId;

public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, UsuarioSistemaId> {

	Optional<UsuarioSistema> findFirstByIdEmailAndStatus(String email, StatusCadastroEnum status);

	boolean existsByIdEmail(String email);
}
