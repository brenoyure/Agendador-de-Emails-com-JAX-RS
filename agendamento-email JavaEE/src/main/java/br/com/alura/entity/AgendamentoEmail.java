package br.com.alura.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class AgendamentoEmail {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column
	@NotBlank(message = "{agendamentoEmail.email.vazio}")
	@Email(message = "{agendamentoEmail.email.invalido}")
	private String email;

	@Column
	@NotBlank(message = "{agendamentoEmail.assunto.vazio}")
	private String assunto;

	@Column
	@NotBlank(message = "{agendamentoEmail.mensagem.vazio}")
	private String mensagem;

	@Column
	private Boolean enviado;

}
