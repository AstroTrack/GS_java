package br.com.fiap.AstroTrack.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.fiap.AstroTrack.dto.ErroResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<ErroResponse> tratarNaoEncontrado(RecursoNaoEncontradoException ex,
			HttpServletRequest request) {
		return construirResposta(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
	}

	@ExceptionHandler({ RegraNegocioException.class, DataIntegrityViolationException.class })
	public ResponseEntity<ErroResponse> tratarRegraDeNegocio(Exception ex, HttpServletRequest request) {
		return construirResposta(HttpStatus.BAD_REQUEST, ex.getMessage(), request, null);
	}

	@ExceptionHandler({ CredenciaisInvalidasException.class, BadCredentialsException.class })
	public ResponseEntity<ErroResponse> tratarCredenciaisInvalidas(Exception ex, HttpServletRequest request) {
		return construirResposta(HttpStatus.UNAUTHORIZED, "Credenciais inválidas", request, null);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErroResponse> tratarAcessoNegado(AccessDeniedException ex, HttpServletRequest request) {
		return construirResposta(HttpStatus.FORBIDDEN, "Acesso negado para este recurso", request, null);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroResponse> tratarValidacao(MethodArgumentNotValidException ex, HttpServletRequest request) {
		Map<String, String> campos = new LinkedHashMap<>();

		for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
			campos.put(erro.getField(), erro.getDefaultMessage());
		}

		return construirResposta(HttpStatus.BAD_REQUEST, "Dados inválidos na requisição", request, campos);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErroResponse> tratarViolacaoDeConstraint(ConstraintViolationException ex,
			HttpServletRequest request) {
		Map<String, String> campos = new LinkedHashMap<>();

		ex.getConstraintViolations()
				.forEach(violacao -> campos.put(violacao.getPropertyPath().toString(), violacao.getMessage()));

		return construirResposta(HttpStatus.BAD_REQUEST, "Dados inválidos na requisição", request, campos);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErroResponse> tratarErroInterno(Exception ex, HttpServletRequest request) {
		return construirResposta(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request, null);
	}

	private ResponseEntity<ErroResponse> construirResposta(HttpStatus status, String mensagem, HttpServletRequest request,
			Map<String, String> campos) {
		ErroResponse erro = new ErroResponse(
				LocalDateTime.now(),
				status.value(),
				status.getReasonPhrase(),
				mensagem,
				request.getRequestURI(),
				campos == null ? Map.of() : campos);

		return ResponseEntity.status(status).body(erro);
	}
}
