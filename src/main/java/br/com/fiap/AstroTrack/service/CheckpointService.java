package br.com.fiap.AstroTrack.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.AstroTrack.dto.CheckpointRequest;
import br.com.fiap.AstroTrack.dto.CheckpointResponse;
import br.com.fiap.AstroTrack.exception.RecursoNaoEncontradoException;
import br.com.fiap.AstroTrack.exception.RegraNegocioException;
import br.com.fiap.AstroTrack.model.Checkpoint;
import br.com.fiap.AstroTrack.model.Coordenada;
import br.com.fiap.AstroTrack.model.StatusViagemEnum;
import br.com.fiap.AstroTrack.model.Viagem;
import br.com.fiap.AstroTrack.repository.CheckpointRepository;
import br.com.fiap.AstroTrack.repository.ViagemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckpointService {

	private final CheckpointRepository checkpointRepository;
	private final ViagemRepository viagemRepository;

	@Transactional(readOnly = true)
	public List<CheckpointResponse> listarTodos() {
		return checkpointRepository.findAll().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public List<CheckpointResponse> listarPorViagem(Long idViagem) {
		if (!viagemRepository.existsById(idViagem)) {
			throw new RecursoNaoEncontradoException("Viagem não localizada com id " + idViagem);
		}

		return checkpointRepository.findByViagemIdViagemOrderByDataRegistroAsc(idViagem).stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public Checkpoint buscarEntidade(Long id) {
		return checkpointRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Checkpoint não localizado com id " + id));
	}

	@Transactional(readOnly = true)
	public CheckpointResponse buscarPorId(Long id) {
		return toResponse(buscarEntidade(id));
	}

	@Transactional
	public CheckpointResponse inserir(CheckpointRequest request) {
		Viagem viagem = buscarViagem(request.idViagem());
		validarCheckpoint(viagem);

		Checkpoint checkpoint = new Checkpoint();
		preencher(checkpoint, request, viagem);

		return toResponse(checkpointRepository.save(checkpoint));
	}

	@Transactional
	public CheckpointResponse atualizar(Long id, CheckpointRequest request) {
		Checkpoint checkpoint = buscarEntidade(id);
		Viagem viagem = buscarViagem(request.idViagem());
		validarCheckpoint(viagem);
		preencher(checkpoint, request, viagem);

		return toResponse(checkpointRepository.save(checkpoint));
	}

	@Transactional
	public void remover(Long id) {
		Checkpoint checkpoint = buscarEntidade(id);
		checkpointRepository.delete(checkpoint);
	}

	private Viagem buscarViagem(Long idViagem) {
		return viagemRepository.findById(idViagem)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Viagem não localizada com id " + idViagem));
	}

	private void validarCheckpoint(Viagem viagem) {
		if (viagem.getStatus() == StatusViagemEnum.FINALIZADA || viagem.getStatus() == StatusViagemEnum.CANCELADA) {
			throw new RegraNegocioException("Não é possível registrar checkpoints em viagens encerradas");
		}
	}

	private void preencher(Checkpoint checkpoint, CheckpointRequest request, Viagem viagem) {
		checkpoint.setViagem(viagem);
		checkpoint.setCoordenada(new Coordenada(request.latitude(), request.longitude()));
		checkpoint.setDataRegistro(request.dataRegistro());
		checkpoint.setBotaoPanico(request.botaoPanico());
		checkpoint.setPortaAberta(request.portaAberta());
	}

	public CheckpointResponse toResponse(Checkpoint checkpoint) {
		return new CheckpointResponse(
				checkpoint.getIdCheckpoint(),
				checkpoint.getViagem().getIdViagem(),
				checkpoint.getCoordenada().getLatitude(),
				checkpoint.getCoordenada().getLongitude(),
				checkpoint.getDataRegistro(),
				checkpoint.getBotaoPanico(),
				checkpoint.getPortaAberta());
	}
}
