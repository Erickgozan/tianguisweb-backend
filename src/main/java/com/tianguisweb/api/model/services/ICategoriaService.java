package com.tianguisweb.api.model.services;

import com.tianguisweb.api.model.entities.Categoria;

public interface ICategoriaService {
	
	//Listar categorias
	//public List<Categoria> findAllCategories();
	//Crear categoria
	public Categoria saveCategoria(Categoria categoria);
	//Eliminar categoria
	public void deleteCategoria(Categoria categoria);
	//Actualizar categoria
	public Categoria findCategoriaById(String id);
}
