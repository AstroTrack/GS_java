package br.com.fiap.AstroTrack.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.AstroTrack.dto.VeiculoRequest;
import br.com.fiap.AstroTrack.dto.VeiculoResponse;
import br.com.fiap.AstroTrack.exception.RecursoNaoEncontradoException;
import br.com.fiap.AstroTrack.exception.RegraNegocioException;
import br.com.fiap.AstroTrack.model.Veiculo;
import br.com.fiap.AstroTrack.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VeiculoService {

	private final VeiculoRepository veiculoRepository;

	@Transactional(readOnly = true)
	public List<VeiculoResponse> listarTodos() {
		return veiculoRepository.findAll().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public Veiculo buscarEntidade(Long id) {
		return veiculoRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Veículo não localizado com id " + id));
	}

	@Transactional(readOnly = true)
	public VeiculoResponse buscarPorId(Long id) {
		return toResponse(buscarEntidade(id));
	}

	@Transactional
	public VeiculoResponse inserir(VeiculoRequest request) {
		validarDuplicidade(request.placa(), null);

		Veiculo veiculo = new Veiculo();
		preencher(veiculo, request);

		return toResponse(veiculoRepository.save(veiculo));
	}

	@Transactional
	public VeiculoResponse atualizar(Long id, VeiculoRequest request) {
		Veiculo veiculo = buscarEntidade(id);
		validarDuplicidade(request.placa(), id);
		preencher(veiculo, request);

		return toResponse(veiculoRepository.save(veiculo));
	}

	@Transactional
	public void remover(Long id) {
		Veiculo veiculo = buscarEntidade(id);
		veiculoRepository.delete(veiculo);
	}

	private void validarDuplicidade(String placa, Long idIgnorado) {
		veiculoRepository.findByPlaca(placa).ifPresent(veiculo -> {
			if (!veiculo.getIdVeiculo().equals(idIgnorado)) {
				throw new RegraNegocioException("Já existe veículo cadastrado com esta placa");
			}
		});
	}

	private void preencher(Veiculo veiculo, VeiculoRequest request) {
		veiculo.setPlaca(request.placa());
		veiculo.setModelo(request.modelo());
		veiculo.setMarca(request.marca());
		veiculo.setAno(request.ano());
		veiculo.setStatus(request.status());
	}

	public VeiculoResponse toResponse(Veiculo veiculo) {
		return new VeiculoResponse(
				veiculo.getIdVeiculo(),
				veiculo.getPlaca(),
				veiculo.getModelo(),
				veiculo.getMarca(),
				veiculo.getAno(),
				veiculo.getStatus());
	}
}
