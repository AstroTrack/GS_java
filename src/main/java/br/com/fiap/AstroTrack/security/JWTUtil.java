package br.com.fiap.AstroTrack.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

	private final SecretKey chave;
	private final Integer expirationMinutes;

	public JWTUtil(
			@Value("${astrotrack.jwt.secret}") String secret,
			@Value("${astrotrack.jwt.expiration-minutes}") Integer expirationMinutes) {
		this.chave = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expirationMinutes = expirationMinutes;
	}

	public String gerarToken(String username) {
		Date dataAtual = new Date();

		JwtBuilder builder = Jwts.builder()
				.subject(username)
				.issuedAt(dataAtual)
				.expiration(new Date(dataAtual.getTime() + (1000L * 60L * expirationMinutes)))
				.signWith(chave);

		return builder.compact();
	}

	public String extrairUsername(String token) {
		try {
			JwtParser parser = Jwts.parser().verifyWith(chave).build();
			return parser.parseSignedClaims(token).getPayload().getSubject();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean validarToken(String token) {
		try {
			JwtParser parser = Jwts.parser().verifyWith(chave).build();
			parser.parseSignedClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
