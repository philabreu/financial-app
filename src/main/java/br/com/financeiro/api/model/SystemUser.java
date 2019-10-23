package br.com.financeiro.api.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SystemUser extends User {

	private static final long serialVersionUID = -4449620150468060886L;

	private Usuario usuario;

	public SystemUser(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getPassword(), authorities);
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	@Override
	public boolean equals(Object rhs) {
		return super.equals(rhs);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
