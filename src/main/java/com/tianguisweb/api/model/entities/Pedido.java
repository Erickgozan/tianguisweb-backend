package com.tianguisweb.api.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {
	private static final long serialVersionUID = 7440437080283543741L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "uuid2")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	@JsonIgnoreProperties({"pedidos", "hibernateLazyInitializer", "handler" })
	private Cliente cliente;
	
	
	@JoinColumn(name = "pedido_id")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}, allowSetters = true)
	private List<ItemProducto> itemProductos;

	@Column(name = "precio_total")
	private Integer precioTotal;
	
	@Column(name = "estado")
	private EstadoPedido estado;

	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date createAt;
	
	public Pedido() {
		this.itemProductos = new ArrayList<ItemProducto>();
	}
		
	@PrePersist
	public void crateAt() {
		this.createAt = new Date();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	

	public List<ItemProducto> getItemProductos() {
		return itemProductos;
	}

	public void setItemProducto(List<ItemProducto> itemProductos) {
		this.itemProductos = itemProductos;
	}

	public Integer getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(Integer precioTotal) {
		this.precioTotal = precioTotal;
	}

	public EstadoPedido getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}
	
	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	
	

}
