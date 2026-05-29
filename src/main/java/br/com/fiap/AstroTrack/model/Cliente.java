package br.com.fiap.AstroTrack.model;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AT_CLIENTES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cliente extends PessoaLogistica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	private Long idCliente;

	@NotBlank(message = "O CNPJ é obrigatório")
	@CNPJ(message = "O CNPJ informado é inválido")
	@Column(name = "cnpj", nullable = false, unique = true, length = 18)
	private String cnpj;

	@NotBlank(message = "O e-mail é obrigatório")
	@Email(message = "O e-mail informado é inválido")
	@Column(name = "email", nullable = false, unique = true, length = 120)
	private String email;
}
