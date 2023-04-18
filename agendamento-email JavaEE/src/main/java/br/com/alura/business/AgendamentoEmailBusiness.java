package br.com.alura.business;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import br.com.alura.dao.AgendamentoEmailDao;
import br.com.alura.entity.AgendamentoEmail;
import br.com.alura.exceptions.BusinessException;
import br.com.alura.interception.Logger;

@Stateless
@Logger
public class AgendamentoEmailBusiness {

	@Inject
	private AgendamentoEmailDao agendamentoEmailDao;

	@Resource(lookup = "java:jboss/mail/AgendamentoMailSession")
	private Session sessaoEmail;

	private static String EMAIL_FROM		=	"mail.address";
	private static String EMAIL_USER		=	"mail.smtp.user";
	private static String EMAIL_PASSWORD	=	"mail.smtp.pass";

	/**
	 * Salva um novo Agendamento de Email.
	 * <br>
	 * Caso o e-mail forncecido já esteja agendado e não tenha sido enviado ainda, uma BusinessException será lançada.
	 * @param agendamentoEmail
	 * @throws BusinessException caso o e-mail forncecido já esteja agendado e ainda não tenha sido enviado.
	 */
	public void salvarAgendamentoEmail(@Valid AgendamentoEmail agendamentoEmail) throws BusinessException {

		if (emailJaAgendado(agendamentoEmail))
			throw new BusinessException("E-mail já agendado.");

		agendamentoEmail.setEnviado(false);
		agendamentoEmailDao.salvarAgendamentoEmail(agendamentoEmail);
	}

	/**
	 * @return Uma lista com todos os Agendamentos cadastrados.
	 */
	public List<AgendamentoEmail> listarAgendamentosEmail() {
		return agendamentoEmailDao.listarAgendamentosEmail();
	}

	/**
	 * @return Uma lista dos Agendamentos ainda não enviados.
	 */
	public List<AgendamentoEmail> listarAgendamentosEmailPorNaoEnviados() {
		return agendamentoEmailDao.listarAgendamentosEmailPorNaoEnviado();
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

		} catch (MessagingException e) {
		    throw new RuntimeException(e);

		}

	}

	/**
	 * Atualiza um Agendamento Email, alterando seu status para enviado.
	 * @param agendamentoEmail que será atualizado
	 */
	public void marcarEnviadas(AgendamentoEmail agendamentoEmail) {
		agendamentoEmail.setEnviado(true);
		agendamentoEmailDao.atualizarAgendamentoEmail(agendamentoEmail);
	}

	private boolean emailJaAgendado(AgendamentoEmail agendamentoEmail) {
		return !agendamentoEmailDao.listarAgendamentosEmailPorEmail(agendamentoEmail.getEmail()).isEmpty();
	}

}
