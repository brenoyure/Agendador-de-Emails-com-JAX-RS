package br.com.alura.entidade;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

	private String email;

	private String assunto;

	private String mensagem;

	private Boolean agendado;

}
