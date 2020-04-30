package br.com.financeiro.api.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pessoa")
@EqualsAndHashCode
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;

	@Getter
	@Setter
	@NotNull
	@Size(min = 3, max = 50)
	private String name;

	@Getter
	@Setter
	@NotNull
	private boolean active;

	@Getter
	@Embedded
	private Address address;

	@JsonIgnore
	@Transient
	public boolean isInactive() {
		return !this.active;
	}
}
