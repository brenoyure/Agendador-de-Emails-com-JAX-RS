package br.com.alura.business;

import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import br.com.alura.dao.AgendamentoEmailDao;
import br.com.alura.entity.AgendamentoEmail;
import br.com.alura.exception.BusinessException;
import br.com.alura.interception.Logger;

@Stateless
@Logger
@TransactionManagement(CONTAINER)
public class AgendamentoEmailBusiness {

	@Inject
	private AgendamentoEmailDao agendamentoEmailDao;

	@Resource(lookup = "java:jboss/mail/AgendamentoMailSession")
	private Session sessaoEmail;
	private static String EMAIL_FROM		= "mail.address";
	private static String EMAIL_USER		= "mail.smtp.user";
	private static String EMAIL_PASSWORD	= "mail.smtp.pass";

	public List<AgendamentoEmail> listarAgendamentosEmail() {
		return agendamentoEmailDao.listarAgendamentosEmail();
	}

	/**
	 * Altera o status do AgendamentoEmail para enviado. 
	 * @param agendamentoEmail
	 */
	private void atualizarStatusAgendamentoEmail(AgendamentoEmail agendamentoEmail) {
		agendamentoEmail.setEnviado(true);
		agendamentoEmailDao.atualizarAgendamentoEmail(agendamentoEmail);
	}

	public List<AgendamentoEmail> listarAgendamentosNaoEnviados() {
		return agendamentoEmailDao.listarAgendamentosNaoEnviados();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void salvarAgendamentoEmail(@Valid AgendamentoEmail agendamentoEmail) throws BusinessException {

		if (!agendamentoEmailDao
				.listarAgendamentoEmailsPorEmail(agendamentoEmail.getEmail())
				.isEmpty()) {
			throw new BusinessException("Email já está agendado.");
		}

		agendamentoEmail.setEnviado(false);
		agendamentoEmailDao.salvarAgendamentoEmail(agendamentoEmail);

	}

	public void enviarEmail(AgendamentoEmail agendamentoEmail) {

		try {

		    MimeMessage mensagem = new MimeMessage(sessaoEmail);
		    mensagem.setFrom(sessaoEmail.getProperty(EMAIL_FROM));
		    mensagem.setRecipients(Message.RecipientType.TO, agendamentoEmail.getEmail());
		    mensagem.setSubject(agendamentoEmail.getAssunto());

		    mensagem.setText(Optional.ofNullable(agendamentoEmail.getMensagem()).orElse(""));

		    Transport.send(mensagem,
		    		sessaoEmail.getProperty(EMAIL_USER),
		    		sessaoEmail.getProperty(EMAIL_PASSWORD));

		    atualizarStatusAgendamentoEmail(agendamentoEmail);


		} catch (MessagingException e) {
		    throw new RuntimeException(e);
		}
		
	}

}
