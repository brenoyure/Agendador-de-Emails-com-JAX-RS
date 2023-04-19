package br.com.alura.servico;

import static java.util.logging.Logger.getLogger;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.alura.dao.AgendamentoEmailDao;
import br.com.alura.entidade.AgendamentoEmail;

@Stateless
public class AgendamentoEmailServico {

	private static final Logger logger = getLogger(AgendamentoEmailServico.class.getName());

	@Inject
	private AgendamentoEmailDao dao;

	public void inserir(AgendamentoEmail agendamentoEmail) {
		agendamentoEmail.setAgendado(false);
		dao.inserir(agendamentoEmail);
	}

	public List<AgendamentoEmail> listarPorNaoAgendado() {
		return dao.listarPorNaoAgendado();
	}

	public List<AgendamentoEmail> listar() {
		return dao.listar();
	}

	public void alterar(AgendamentoEmail agendamentoEmail) {
		agendamentoEmail.setAgendado(true);
		dao.alterar(agendamentoEmail);
	}

	public void enviar(AgendamentoEmail agendamentoEmail) {

		try {
			Thread.sleep(5000);
			logger.info("O email do usuário " + agendamentoEmail.getEmail() + " foi enviado.");
		} catch (InterruptedException e) {
			logger.warning(e.getMessage());
		}

	}

}
