package br.com.fiap.AstroTrack.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/")
	public String home() {
		return "AstroTrack API está rodando!";
	}

	@GetMapping("/health")
	public String health() {
		return "OK";
	}
}
