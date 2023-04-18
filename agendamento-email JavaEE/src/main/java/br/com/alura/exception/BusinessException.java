package br.com.alura.exception;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.ApplicationException;

import lombok.Getter;

@Getter
@ApplicationException(rollback = true)
public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private List<String> mensagens;

	public BusinessException() {
		super();
		mensagens = new ArrayList<>();
	}

	public BusinessException(String mensagem) {
		super(mensagem);
		mensagens = new ArrayList<>();
		mensagens.add(mensagem);
	}
	
	public void addMensagem(String mensagem) {
		this.mensagens.add(mensagem);
	}
}
