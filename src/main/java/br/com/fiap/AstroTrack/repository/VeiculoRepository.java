package br.com.fiap.AstroTrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.AstroTrack.model.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

	boolean existsByPlaca(String placa);

	Optional<Veiculo> findByPlaca(String placa);
}
