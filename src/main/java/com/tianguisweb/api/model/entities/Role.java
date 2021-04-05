package com.tianguisweb.api.model.entities;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name = "roles")
public class Role implements Serializable{
	private static final long serialVersionUID = -4586649236497616469L;

	@Id
	private String id;

	@Column(unique = true, length = 20)
	private String nombre;
	

	public String getId() {
		return id;
	}	

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
