package br.com.alura.timer;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

import br.com.alura.business.AgendamentoEmailBusiness;

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
	public void enviarEmailsAgendados() {

		agendamentoEmailBusiness
			.listarAgendamentosEmailPorNaoEnviados()
			.stream()
			.forEach(agendamentoEmail -> {
				jmsContext.createProducer().send(emailQueue, agendamentoEmail);
				agendamentoEmailBusiness.marcarEnviadas(agendamentoEmail);
			});

	}

}
