package br.com.alura;

import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.alura.dto.MensagemErroDto;
import br.com.alura.exceptions.BusinessException;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

	@Override
	public Response toResponse(BusinessException businessException) {
		return status(BAD_REQUEST)
				.entity(MensagemErroDto.build(businessException.getMensagens()))
				.build();
	}

}
