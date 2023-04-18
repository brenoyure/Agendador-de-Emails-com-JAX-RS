package br.com.alura.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import br.com.alura.business.AgendamentoEmailBusiness;
import br.com.alura.entity.AgendamentoEmail;
import br.com.alura.interception.Logger;

@Logger
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType"	, propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/EmailQueue") })
public class EmailMDB implements MessageListener {

	@Inject
	private AgendamentoEmailBusiness agendamentoEmailBusiness;

	@Override
	public void onMessage(Message message) {

		try {
			agendamentoEmailBusiness.enviarEmail(
					message.getBody(AgendamentoEmail.class));

		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
		
		
	}

}
