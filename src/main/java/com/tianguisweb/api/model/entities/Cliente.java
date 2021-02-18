package com.tianguisweb.api.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
	@Column(name = "apellido_paterno")
	private String apellidoPaterno;
	@NotBlank(message = "no puede estar vació")
	@Column(name = "apellido_materno")
	private String apellidoMaterno;
	
	@NotBlank(message = "no puede estar vació")
	private String telefono;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "direccion_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Direccion direccion;

	@ManyToMany(fetch = FetchType.LAZY )
	@JoinTable(name = "clientes_productos", joinColumns = @JoinColumn(name = "cliente_id"),
	inverseJoinColumns = @JoinColumn(name = "producto_id"),
	uniqueConstraints = @UniqueConstraint(columnNames = {
			"cliente_id", "producto_id" }))
	private List<Producto> productos;
	
	@Column(name = "fecha_compra")
	@Temporal(TemporalType.DATE)
	private Date fechaCompra;
	
	public Cliente() {
		this.productos = new ArrayList<Producto>();
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

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		
		this.productos = productos;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}


}
