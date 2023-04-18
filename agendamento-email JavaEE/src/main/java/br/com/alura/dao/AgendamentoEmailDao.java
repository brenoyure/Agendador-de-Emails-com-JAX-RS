package br.com.alura.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.alura.entity.AgendamentoEmail;

@Stateless
public class AgendamentoEmailDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void salvarAgendamentoEmail(AgendamentoEmail agendamentoEmail) {
		entityManager.persist(agendamentoEmail);
	}

	public void atualizarAgendamentoEmail(AgendamentoEmail agendamentoEmail) {
		entityManager.merge(agendamentoEmail);
	}

	public List<AgendamentoEmail> listarAgendamentosEmail() {
		return entityManager
				.createQuery("FROM AgendamentoEmail ae", AgendamentoEmail.class)
				.getResultList();
	}

	public List<AgendamentoEmail> listarAgendamentosEmailPorNaoEnviado() {
		return entityManager
				.createQuery("FROM AgendamentoEmail ae WHERE ae.enviado=false", AgendamentoEmail.class)
				.getResultList();
	}

	public List<AgendamentoEmail> listarAgendamentosEmailPorEmail(String email) {
		TypedQuery<AgendamentoEmail> query = entityManager
				.createQuery("FROM AgendamentoEmail ae WHERE ae.enviado=false AND email=:email", AgendamentoEmail.class);
		query.setParameter("email", email);

		return query.getResultList();

	}

}
