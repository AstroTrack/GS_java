package br.com.fiap.AstroTrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfiguration {

	private static final String TIPO_AUTENTICACAO = "bearerAuth";

	@Bean
	OpenAPI configurarSwagger() {
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList(TIPO_AUTENTICACAO))
				.components(new Components()
						.addSecuritySchemes(TIPO_AUTENTICACAO,
								new SecurityScheme()
										.name(TIPO_AUTENTICACAO)
										.type(SecurityScheme.Type.HTTP)
										.scheme("bearer")
										.bearerFormat("JWT")))
				.info(new Info()
						.title("AstroTrack - FIAP 2026")
						.description("API REST para gestão de logística satelital, frotas, viagens e checkpoints em zonas remotas")
						.summary("Monitoramento satelital de operações logísticas")
						.termsOfService("Uso acadêmico FIAP")
						.version("1.0.0")
						.license(new License().url("/licenses").name("FIAP")));
	}
}
