package com.tianguisweb.api.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "direcciones")
public class Direccion implements Serializable {
	private static final long serialVersionUID = 4864288427892749481L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "uuid2")
	private String id;

	@NotBlank(message = "no puede estar vació")
	private String calle;

	@NotBlank(message = "no puede estar vació")
	private String colonia;

	@Column(name = "no_exterior")
	@NotNull(message = "no puede estar vació")
	private Integer noExterior;

	@Column(name = "no_interior")
	private Integer noInterior;

	@NotBlank(message = "no puede estar vació")
	private String municipio;

	@NotNull(message = "no puede estar vació")
	Integer cp;

	@NotBlank(message = "no puede estar vació")
	private String estado;
	
	@OneToMany(mappedBy = "direccion")
	private List<Cliente> clientes;
	
	public Direccion() {
		this.clientes = new ArrayList<Cliente>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public Integer getNoExterior() {
		return noExterior;
	}

	public void setNoExterior(Integer noExterior) {
		this.noExterior = noExterior;
	}

	public Integer getNoInterior() {
		return noInterior;
	}

	public void setNoInterior(Integer noInterior) {
		this.noInterior = noInterior;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Integer getCp() {
		return cp;
	}

	public void setCp(Integer cp) {
		this.cp = cp;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}



}
