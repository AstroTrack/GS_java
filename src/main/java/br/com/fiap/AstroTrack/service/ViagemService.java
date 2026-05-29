package br.com.fiap.AstroTrack.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.AstroTrack.dto.ViagemRequest;
import br.com.fiap.AstroTrack.dto.ViagemResponse;
import br.com.fiap.AstroTrack.exception.RecursoNaoEncontradoException;
import br.com.fiap.AstroTrack.exception.RegraNegocioException;
import br.com.fiap.AstroTrack.model.Cliente;
import br.com.fiap.AstroTrack.model.Motorista;
import br.com.fiap.AstroTrack.model.Rota;
import br.com.fiap.AstroTrack.model.StatusCadastroEnum;
import br.com.fiap.AstroTrack.model.StatusVeiculoEnum;
import br.com.fiap.AstroTrack.model.StatusViagemEnum;
import br.com.fiap.AstroTrack.model.Veiculo;
import br.com.fiap.AstroTrack.model.Viagem;
import br.com.fiap.AstroTrack.repository.ClienteRepository;
import br.com.fiap.AstroTrack.repository.MotoristaRepository;
import br.com.fiap.AstroTrack.repository.VeiculoRepository;
import br.com.fiap.AstroTrack.repository.ViagemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViagemService {

	private final ViagemRepository viagemRepository;
	private final ClienteRepository clienteRepository;
	private final MotoristaRepository motoristaRepository;
	private final VeiculoRepository veiculoRepository;

	@Transactional(readOnly = true)
	public List<ViagemResponse> listarTodos() {
		return viagemRepository.findAll().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public List<ViagemResponse> listarPorStatus(StatusViagemEnum status) {
		return viagemRepository.findByStatus(status).stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public Viagem buscarEntidade(Long id) {
		return viagemRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Viagem não localizada com id " + id));
	}

	@Transactional(readOnly = true)
	public ViagemResponse buscarPorId(Long id) {
		return toResponse(buscarEntidade(id));
	}

	@Transactional
	public ViagemResponse inserir(ViagemRequest request) {
		Cliente cliente = buscarCliente(request.idCliente());
		Motorista motorista = buscarMotorista(request.idMotorista());
		Veiculo veiculo = buscarVeiculo(request.idVeiculo());

		validarViagem(request, null, cliente, motorista, veiculo);

		Viagem viagem = new Viagem();
		preencher(viagem, request, cliente, motorista, veiculo);
		sincronizarStatusVeiculo(veiculo, request.status());

		return toResponse(viagemRepository.save(viagem));
	}

	@Transactional
	public ViagemResponse atualizar(Long id, ViagemRequest request) {
		Viagem viagem = buscarEntidade(id);
		Cliente cliente = buscarCliente(request.idCliente());
		Motorista motorista = buscarMotorista(request.idMotorista());
		Veiculo veiculo = buscarVeiculo(request.idVeiculo());

		validarViagem(request, id, cliente, motorista, veiculo);
		preencher(viagem, request, cliente, motorista, veiculo);
		sincronizarStatusVeiculo(veiculo, request.status());

		return toResponse(viagemRepository.save(viagem));
	}

	@Transactional
	public void remover(Long id) {
		Viagem viagem = buscarEntidade(id);
		if (viagem.getStatus() == StatusViagemEnum.EM_ANDAMENTO) {
			throw new RegraNegocioException("Não é possível remover uma viagem em andamento");
		}
		viagemRepository.delete(viagem);
	}

	private Cliente buscarCliente(Long idCliente) {
		return clienteRepository.findById(idCliente)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não localizado com id " + idCliente));
	}

	private Motorista buscarMotorista(Long idMotorista) {
		return motoristaRepository.findById(idMotorista)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Motorista não localizado com id " + idMotorista));
	}

	private Veiculo buscarVeiculo(Long idVeiculo) {
		return veiculoRepository.findById(idVeiculo)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Veículo não localizado com id " + idVeiculo));
	}

	private void validarViagem(ViagemRequest request, Long idViagemIgnorada, Cliente cliente, Motorista motorista,
			Veiculo veiculo) {
		if (cliente.getStatus() != StatusCadastroEnum.ATIVO) {
			throw new RegraNegocioException("Cliente precisa estar ativo para receber uma viagem");
		}

		if (motorista.getStatus() != StatusCadastroEnum.ATIVO) {
			throw new RegraNegocioException("Motorista precisa estar ativo para iniciar uma viagem");
		}

		if (veiculo.getStatus() == StatusVeiculoEnum.EM_MANUTENCAO || veiculo.getStatus() == StatusVeiculoEnum.INATIVO) {
			throw new RegraNegocioException("Veículo não está disponível para operação");
		}

		if (request.dataFim() != null && request.dataFim().isBefore(request.dataInicio())) {
			throw new RegraNegocioException("A data de fim não pode ser anterior à data de início");
		}

		if (request.status() == StatusViagemEnum.FINALIZADA && request.dataFim() == null) {
			throw new RegraNegocioException("Viagens finalizadas precisam informar data de fim");
		}

		if (request.status() == StatusViagemEnum.EM_ANDAMENTO) {
			validarRecursoEmViagem(idViagemIgnorada, motorista.getIdMotorista(), veiculo.getIdVeiculo());
		}
	}

	private void validarRecursoEmViagem(Long idViagemIgnorada, Long idMotorista, Long idVeiculo) {
		viagemRepository.findAll().stream()
				.filter(viagem -> viagem.getStatus() == StatusViagemEnum.EM_ANDAMENTO)
				.filter(viagem -> !viagem.getIdViagem().equals(idViagemIgnorada))
				.filter(viagem -> viagem.getMotorista().getIdMotorista().equals(idMotorista)
						|| viagem.getVeiculo().getIdVeiculo().equals(idVeiculo))
				.findFirst()
				.ifPresent(viagem -> {
					throw new RegraNegocioException("Motorista ou veículo já está vinculado a uma viagem em andamento");
				});
	}

	private void preencher(Viagem viagem, ViagemRequest request, Cliente cliente, Motorista motorista, Veiculo veiculo) {
		viagem.setCliente(cliente);
		viagem.setMotorista(motorista);
		viagem.setVeiculo(veiculo);
		viagem.setRota(new Rota(request.origem(), request.destino()));
		viagem.setDataInicio(request.dataInicio());
		viagem.setDataFim(request.dataFim());
		viagem.setStatus(request.status());
		viagem.setQuilometragemTotal(request.quilometragemTotal());
	}

	private void sincronizarStatusVeiculo(Veiculo veiculo, StatusViagemEnum statusViagem) {
		if (statusViagem == StatusViagemEnum.EM_ANDAMENTO) {
			veiculo.setStatus(StatusVeiculoEnum.EM_VIAGEM);
			return;
		}

		if (statusViagem == StatusViagemEnum.FINALIZADA || statusViagem == StatusViagemEnum.CANCELADA) {
			veiculo.setStatus(StatusVeiculoEnum.DISPONIVEL);
		}
	}

	public ViagemResponse toResponse(Viagem viagem) {
		return new ViagemResponse(
				viagem.getIdViagem(),
				viagem.getCliente().getIdCliente(),
				viagem.getCliente().getNome(),
				viagem.getMotorista().getIdMotorista(),
				viagem.getMotorista().getNome(),
				viagem.getVeiculo().getIdVeiculo(),
				viagem.getVeiculo().getPlaca(),
				viagem.getRota().getOrigem(),
				viagem.getRota().getDestino(),
				viagem.getDataInicio(),
				viagem.getDataFim(),
				viagem.getStatus(),
				viagem.getQuilometragemTotal());
	}
}
