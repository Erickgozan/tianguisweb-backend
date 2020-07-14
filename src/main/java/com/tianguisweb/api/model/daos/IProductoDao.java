package com.tianguisweb.api.model.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tianguisweb.api.model.entities.Producto;

public interface IProductoDao extends JpaRepository<Producto, Long>{

}
