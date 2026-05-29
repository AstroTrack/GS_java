package br.com.fiap.AstroTrack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.AstroTrack.model.StatusViagemEnum;
import br.com.fiap.AstroTrack.model.Viagem;

public interface ViagemRepository extends JpaRepository<Viagem, Long> {

	boolean existsByMotoristaIdMotoristaAndStatus(Long idMotorista, StatusViagemEnum status);

	boolean existsByVeiculoIdVeiculoAndStatus(Long idVeiculo, StatusViagemEnum status);

	List<Viagem> findByClienteIdCliente(Long idCliente);

	List<Viagem> findByStatus(StatusViagemEnum status);
}
