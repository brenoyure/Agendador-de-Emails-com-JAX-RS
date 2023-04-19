package br.com.alura.controllers;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.CREATED;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.alura.entidade.AgendamentoEmail;
import br.com.alura.servico.AgendamentoEmailServico;

@Path("emails")
public class AgendamentoEmailController {

	@Inject
	private AgendamentoEmailServico servico;

	@POST
	@Consumes(APPLICATION_JSON)
	public Response inserir(AgendamentoEmail agendamentoEmail) {
		servico.inserir(agendamentoEmail);
		return status(CREATED).build();
	}

	@GET
	@Produces(APPLICATION_JSON)
	public Response listar() {
		return ok(servico.listar()).build();
	}

}
