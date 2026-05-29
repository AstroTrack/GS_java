package br.com.fiap.AstroTrack.model;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AT_MOTORISTAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Motorista extends PessoaLogistica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_motorista")
	private Long idMotorista;

	@NotBlank(message = "O CPF é obrigatório")
	@CPF(message = "O CPF informado é inválido")
	@Column(name = "cpf", nullable = false, unique = true, length = 14)
	private String cpf;

	@NotBlank(message = "A CNH é obrigatória")
	@Pattern(regexp = "\\d{11}", message = "A CNH deve possuir 11 dígitos")
	@Column(name = "cnh", nullable = false, unique = true, length = 11)
	private String cnh;
}
