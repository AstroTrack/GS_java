package br.com.fiap.AstroTrack.control;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.AstroTrack.service.HateoasMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class HateoasController {

	private final HateoasMenuService hateoasMenuService;

	@Operation(summary = "Menu HATEOAS geral", description = "Retorna os links principais da API AstroTrack")
	@GetMapping("/hateoas")
	public ResponseEntity<RepresentationModel<?>> hateoas() {
		return ResponseEntity.ok(hateoasMenuService.apiMenu());
	}
}
