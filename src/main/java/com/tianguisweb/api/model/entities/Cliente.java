package com.tianguisweb.api.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Usuario implements Serializable {
	private static final long serialVersionUID = 5595868450556698746L;

	@NotBlank(message = "no puede estar vació")
	private String nombre;
	
	@NotBlank(message = "no puede estar vació")
	@Column(name = "apellido_paterno")
	private String apellidoPaterno;
	
	@Column(name = "apellido_materno")
	private String apellidoMaterno;

	@NotBlank(message = "no puede estar vació")
	private String telefono;

	@NotNull(message = "(CP, Calle, Colonia, No. Exterior, Municipio, Estado) no pueden estar vaciós")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	@JoinColumn(name = "direccion_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Direccion direccion;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente",cascade = CascadeType.ALL)
	//@JsonIgnore
	private List<Pedido> pedido;


	public Cliente() {
		this.pedido = new ArrayList<Pedido>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
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

	public List<Pedido> getPedido() {
		return pedido;
	}

	public void setPedido(List<Pedido> pedido) {
		this.pedido = pedido;
	}
	

}
