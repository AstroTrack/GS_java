package br.com.fiap.AstroTrack.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.AstroTrack.dto.ClienteRequest;
import br.com.fiap.AstroTrack.dto.ClienteResponse;
import br.com.fiap.AstroTrack.exception.RecursoNaoEncontradoException;
import br.com.fiap.AstroTrack.exception.RegraNegocioException;
import br.com.fiap.AstroTrack.model.Cliente;
import br.com.fiap.AstroTrack.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

	private final ClienteRepository clienteRepository;

	@Transactional(readOnly = true)
	public List<ClienteResponse> listarTodos() {
		return clienteRepository.findAll().stream().map(this::toResponse).toList();
	}

	@Transactional(readOnly = true)
	public Cliente buscarEntidade(Long id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não localizado com id " + id));
	}

	@Transactional(readOnly = true)
	public ClienteResponse buscarPorId(Long id) {
		return toResponse(buscarEntidade(id));
	}

	@Transactional
	public ClienteResponse inserir(ClienteRequest request) {
		validarDuplicidade(request.cnpj(), request.email(), null);

		Cliente cliente = new Cliente();
		preencher(cliente, request);

		return toResponse(clienteRepository.save(cliente));
	}

	@Transactional
	public ClienteResponse atualizar(Long id, ClienteRequest request) {
		Cliente cliente = buscarEntidade(id);
		validarDuplicidade(request.cnpj(), request.email(), id);
		preencher(cliente, request);

		return toResponse(clienteRepository.save(cliente));
	}

	@Transactional
	public void remover(Long id) {
		Cliente cliente = buscarEntidade(id);
		clienteRepository.delete(cliente);
	}

	private void validarDuplicidade(String cnpj, String email, Long idIgnorado) {
		clienteRepository.findByCnpj(cnpj).ifPresent(cliente -> {
			if (!cliente.getIdCliente().equals(idIgnorado)) {
				throw new RegraNegocioException("Já existe cliente cadastrado com este CNPJ");
			}
		});

		clienteRepository.findAll().stream()
				.filter(cliente -> cliente.getEmail().equalsIgnoreCase(email))
				.filter(cliente -> !cliente.getIdCliente().equals(idIgnorado))
				.findFirst()
				.ifPresent(cliente -> {
					throw new RegraNegocioException("Já existe cliente cadastrado com este e-mail");
				});
	}

	private void preencher(Cliente cliente, ClienteRequest request) {
		cliente.setNome(request.nome());
		cliente.setCnpj(request.cnpj());
		cliente.setEmail(request.email());
		cliente.setTelefone(request.telefone());
		cliente.setStatus(request.status());
	}

	public ClienteResponse toResponse(Cliente cliente) {
		return new ClienteResponse(
				cliente.getIdCliente(),
				cliente.getNome(),
				cliente.getCnpj(),
				cliente.getEmail(),
				cliente.getTelefone(),
				cliente.getStatus());
	}
}
