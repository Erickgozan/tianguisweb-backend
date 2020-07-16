package com.tianguisweb.api.model.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria implements Serializable {
	private static final long serialVersionUID = -4896774368399916324L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	

}
