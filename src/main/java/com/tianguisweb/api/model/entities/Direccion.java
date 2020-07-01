package com.tianguisweb.api.model.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "direcciones")
public class Direccion implements Serializable {
	private static final long serialVersionUID = 4864288427892749481L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String calle;
	
	private String colonia;
	
	@Column(name = "no_exterior")
	private Integer noExterior;
	
	@Column(name = "no_interior")
	private Integer noInterior;
	
	private String municipio;
	
	private Integer cp;
	
	private String estado;
	
	@OneToMany(mappedBy = "direcciones")
	private List<Cliente> clientes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public List<Cliente> getUsuarios() {
		return clientes;
	}

	public void setUsuarios(List<Cliente> clientes) {
		this.clientes = clientes;
	}

}
