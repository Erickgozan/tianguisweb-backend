package com.tianguisweb.api.model.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {
	private static final long serialVersionUID = -7261566280398676761L;

	/*@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator",parameters = {
	   @Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.CustomerVersionOneStretegy")})*/
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name="UUID", strategy = "uuid2")
	private String id;

	@NotBlank(message = "no pede estar vació")
	private String nombre;

	@NotNull(message = "no puede estar vació")
	private Double precio;

	@NotBlank(message = "no puede estar vació")
	@Column(length = 2000)
	private String descripcion;

	@NotBlank(message = "no puede estar vació")
	@Column(length = 2000)
	private String caracteristicas;

	@NotNull(message = "no puede estar vació")
	private Integer stock;

	@NotNull(message = "no puede estar vació")
	private Boolean oferta;

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoria_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@NotNull(message = "no puede estar vacío")
	private Categoria categoria;

	@Column(name = "img_1")
	private String img1;

	@Column(name = "img_2")
	private String img2;

	@Column(name = "img_3")
	private String img3;

	@Column(name = "img_4")
	private String img4;

	@Column(name = "img_5")
	private String img5;

	@PrePersist
	public void createAt() {
		this.createAt = new Date();
	}

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

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer cantidad) {
		this.stock = cantidad;
	}

	public Boolean getOferta() {
		return oferta;
	}

	public void setOferta(Boolean oferta) {
		this.oferta = oferta;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}

	public String getImg4() {
		return img4;
	}

	public void setImg4(String img4) {
		this.img4 = img4;
	}

	public String getImg5() {
		return img5;
	}

	public void setImg5(String img5) {
		this.img5 = img5;
	}

}
