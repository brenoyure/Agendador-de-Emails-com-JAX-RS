package br.com.alura.exceptions;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

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

	public List<String> getMensagens() {
		return unmodifiableList(mensagens);
	}

}
