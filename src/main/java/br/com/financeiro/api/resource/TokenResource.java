package br.com.financeiro.api.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenResource {

	@DeleteMapping("/revogar")
	public void revogar(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		cookie.setSecure(false);
		cookie.setPath(servletRequest.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0);

		servletResponse.addCookie(cookie);
		servletResponse.setStatus(HttpStatus.NO_CONTENT.value());

	}

}
