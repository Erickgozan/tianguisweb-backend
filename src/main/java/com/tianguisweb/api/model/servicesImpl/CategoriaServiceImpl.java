package com.tianguisweb.api.model.servicesImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianguisweb.api.model.daos.ICategoriaDao;
import com.tianguisweb.api.model.entities.Categoria;
import com.tianguisweb.api.model.services.ICategoriaService;

@Service
@Transactional
public class CategoriaServiceImpl implements ICategoriaService {

	@Autowired
	private ICategoriaDao categoriaDao;
	
	//Guardar categoria
	@Override
	public Categoria saveCategoria(Categoria categoria) {

		return categoriaDao.save(categoria);
	}
	
	//Eliminar categoria
	@Override
	public void deleteCategoria(Categoria categoria) {
		if (categoria != null) {
			categoriaDao.delete(categoria);
		}

	}
	
	//Buscar categoria por su id
	@Override
	public Categoria findCategoriaById(Long id) {

		return this.categoriaDao.findById(id).orElse(null);
	}

}
