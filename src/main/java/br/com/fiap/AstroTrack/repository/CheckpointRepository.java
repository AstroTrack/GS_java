package br.com.fiap.AstroTrack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.AstroTrack.model.Checkpoint;

public interface CheckpointRepository extends JpaRepository<Checkpoint, Long> {

	List<Checkpoint> findByViagemIdViagemOrderByDataRegistroAsc(Long idViagem);
}
