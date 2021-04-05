package com.tianguisweb.api.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Usuario implements Serializable {
	private static final long serialVersionUID = 5595868450556698746L;
	
	
	@NotBlank(message = "no puede estar vació")
	private String nombre;
	
	@NotBlank(message = "no puede estar vació")
	private String apellido;
 
	@NotBlank(message = "no puede estar vació")
	private String telefono;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "direccion_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Direccion direccion;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente",cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value={"cliente","hibernateLazyInitializer","handler"})
	private List<Pedido> pedidos;


	public Cliente() {
		this.pedidos = new ArrayList<Pedido>();
	}
		

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	

	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	

}
