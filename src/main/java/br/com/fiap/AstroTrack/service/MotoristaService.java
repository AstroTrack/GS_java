package br.com.fiap.AstroTrack.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.AstroTrack.dto.MotoristaRequest;
import br.com.fiap.AstroTrack.dto.MotoristaResponse;
import br.com.fiap.AstroTrack.exception.RecursoNaoEncontradoException;
import br.com.fiap.AstroTrack.exception.RegraNegocioException;
import br.com.fiap.AstroTrack.model.Motorista;
import br.com.fiap.AstroTrack.repository.MotoristaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MotoristaService {

	private final MotoristaRepository motoristaRepository;

	@Transactional(readOnly = true)
	public List<MotoristaResponse> listarTodos() {
		return motoristaRepository.findAll().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public Motorista buscarEntidade(Long id) {
		return motoristaRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Motorista não localizado com id " + id));
	}

	@Transactional(readOnly = true)
	public MotoristaResponse buscarPorId(Long id) {
		return toResponse(buscarEntidade(id));
	}

	@Transactional
	public MotoristaResponse inserir(MotoristaRequest request) {
		validarDuplicidade(request.cpf(), request.cnh(), null);

		Motorista motorista = new Motorista();
		preencher(motorista, request);

		return toResponse(motoristaRepository.save(motorista));
	}

	@Transactional
	public MotoristaResponse atualizar(Long id, MotoristaRequest request) {
		Motorista motorista = buscarEntidade(id);
		validarDuplicidade(request.cpf(), request.cnh(), id);
		preencher(motorista, request);

		return toResponse(motoristaRepository.save(motorista));
	}

	@Transactional
	public void remover(Long id) {
		Motorista motorista = buscarEntidade(id);
		motoristaRepository.delete(motorista);
	}

	private void validarDuplicidade(String cpf, String cnh, Long idIgnorado) {
		motoristaRepository.findByCpf(cpf).ifPresent(motorista -> {
			if (!motorista.getIdMotorista().equals(idIgnorado)) {
				throw new RegraNegocioException("Já existe motorista cadastrado com este CPF");
			}
		});

		motoristaRepository.findAll().stream()
				.filter(motorista -> motorista.getCnh().equals(cnh))
				.filter(motorista -> !motorista.getIdMotorista().equals(idIgnorado))
				.findFirst()
				.ifPresent(motorista -> {
					throw new RegraNegocioException("Já existe motorista cadastrado com esta CNH");
				});
	}

	private void preencher(Motorista motorista, MotoristaRequest request) {
		motorista.setNome(request.nome());
		motorista.setCpf(request.cpf());
		motorista.setCnh(request.cnh());
		motorista.setTelefone(request.telefone());
		motorista.setStatus(request.status());
	}

	public MotoristaResponse toResponse(Motorista motorista) {
		return new MotoristaResponse(
				motorista.getIdMotorista(),
				motorista.getNome(),
				motorista.getCpf(),
				motorista.getCnh(),
				motorista.getTelefone(),
				motorista.getStatus());
	}
}
