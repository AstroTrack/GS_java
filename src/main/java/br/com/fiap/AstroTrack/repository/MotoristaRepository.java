package br.com.fiap.AstroTrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.AstroTrack.model.Motorista;

public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

	boolean existsByCpf(String cpf);

	boolean existsByCnh(String cnh);

	Optional<Motorista> findByCpf(String cpf);
}
