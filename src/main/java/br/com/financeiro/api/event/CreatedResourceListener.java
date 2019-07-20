package br.com.financeiro.api.event;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class CreatedResourceListener implements ApplicationListener<CreatedResourceEvent> {

	@Override
	public void onApplicationEvent(CreatedResourceEvent createdResourceEvent) {

		HttpServletResponse response = createdResourceEvent.getResponse();
		Long idRecurso = createdResourceEvent.getId();

		adicionarHeadLocation(response, idRecurso);
	}

	private void adicionarHeadLocation(HttpServletResponse response, Long idRecurso) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(idRecurso).toUri();

		response.setHeader("Location", uri.toASCIIString());
	}

}
