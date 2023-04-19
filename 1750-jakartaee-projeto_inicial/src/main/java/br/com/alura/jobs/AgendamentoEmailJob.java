package br.com.alura.jobs;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

import br.com.alura.servico.AgendamentoEmailServico;

@Singleton
@TransactionManagement(CONTAINER)
public class AgendamentoEmailJob {

	@Inject
	private AgendamentoEmailServico agendamentoEmailServico;
	
	@Inject
	@JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
	private JMSContext context;

	@Resource(mappedName = "java:/jms/queue/EmailQueue")
	private Queue queue;
	
	@TransactionAttribute(REQUIRED)
	@Schedule(hour = "*", minute = "*", second = "*/10")
	public void enviarEmail() {
		
		JMSProducer producer = context.createProducer();
		
		agendamentoEmailServico
			.listarPorNaoAgendado()
			.forEach(emailNaoAgendado -> {
				producer.send(queue, emailNaoAgendado);
				agendamentoEmailServico.alterar(emailNaoAgendado);
			});
	}

}
