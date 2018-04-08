package br.com.financeiro.api.event;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

	@Override
	public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {

		HttpServletResponse response = recursoCriadoEvent.getResponse();
		Long idRecurso = recursoCriadoEvent.getId();

		adicionarHeadLocation(response, idRecurso);
	}

	private void adicionarHeadLocation(HttpServletResponse response, Long idRecurso) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(idRecurso).toUri();

		response.setHeader("Location", uri.toASCIIString());
	}

}
