package br.com.fiap.AstroTrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.AstroTrack.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	boolean existsByCnpj(String cnpj);

	boolean existsByEmail(String email);

	Optional<Cliente> findByCnpj(String cnpj);
}
