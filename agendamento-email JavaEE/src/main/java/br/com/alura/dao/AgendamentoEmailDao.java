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

	public List<AgendamentoEmail> listarAgendamentosEmail() {
		return entityManager
				.createQuery("SELECT ae FROM AgendamentoEmail ae", AgendamentoEmail.class)
				.getResultList();
	}
	
	public void salvarAgendamentoEmail(AgendamentoEmail agendamentoEmail) {
		entityManager.persist(agendamentoEmail);
	}

	public void atualizarAgendamentoEmail(AgendamentoEmail agendamentoEmail) {
		entityManager.merge(agendamentoEmail);
	}

	public List<AgendamentoEmail> listarAgendamentoEmailsPorEmail(String email) {
		TypedQuery<AgendamentoEmail> query = entityManager
				.createQuery("SELECT a FROM AgendamentoEmail a WHERE a.email = :email AND a.enviado = false", AgendamentoEmail.class);
		query.setParameter("email", email);
		
		return query.getResultList();
		
	}
	
	public List<AgendamentoEmail> listarAgendamentosNaoEnviados() {
		TypedQuery<AgendamentoEmail> query = entityManager
				.createQuery("SELECT a FROM AgendamentoEmail a WHERE a.enviado = false", AgendamentoEmail.class);
		return query.getResultList();
		
	}

}
