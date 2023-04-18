package br.com.alura.timer;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

import br.com.alura.business.AgendamentoEmailBusiness;
import br.com.alura.entity.AgendamentoEmail;

@Singleton
public class AgendamentoEmailTimer {

	@Inject
	private AgendamentoEmailBusiness agendamentoEmailBusiness;

	@Inject
	@JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
	private JMSContext jmsContext;
	
	@Resource(mappedName =  "java:/jms/queue/EmailQueue")
	private Queue emailQueue;

	@Schedule(hour = "*", minute = "*", second = "*/10")
	public void enviarEmailsAgendado() {
		List<AgendamentoEmail> agendamentoEmails = agendamentoEmailBusiness.listarAgendamentosNaoEnviados();

		JMSProducer jmsProducer = jmsContext.createProducer();

		agendamentoEmails
			.stream()
			.forEach(agendamentoEmail -> {
				jmsProducer.send(emailQueue, agendamentoEmail);
			});
		
	}

}
