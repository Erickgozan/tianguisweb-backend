package com.tianguisweb.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianguisweb.api.model.entities.Categoria;
import com.tianguisweb.api.model.services.ICategoriaService;

@RestController
@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RequestMapping("/api")
public class CategoriaController {

	@Autowired
	private ICategoriaService categoriaService;

	// Metodo POST para crear la categoria
	@PostMapping("productos/categorias/create")
	public ResponseEntity<?> createCategories(@Valid @RequestBody Categoria categoria, BindingResult result) {

		Map<String, Object> response = new HashMap<String, Object>();
		Categoria nuevaCategoria = null;

		if (result.hasErrors()) {

			List<String> errores = result.getFieldErrors().stream()
					.map(e -> "El campo '" + e.getField() + "' " + e.getDefaultMessage()).collect(Collectors.toList());
			response.put("error_400", errores);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			nuevaCategoria = categoriaService.saveCategoria(categoria);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert a la base de datos");
			response.put("error", e.getMostSpecificCause().getLocalizedMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Se agrego correctamente la categoria");
		response.put("categoria", nuevaCategoria);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Metodo PUT para editar la categoria
	@PutMapping("productos/categorias/update/{id}")
	public ResponseEntity<?> updateCategories(@Valid @RequestBody Categoria categoria, BindingResult result,
			@PathVariable Long id) {

		Categoria categoriaActural = this.categoriaService.findCategoriaById(id);
		Categoria nuevaCategoria = null;
		Map<String, Object> response = new HashMap<String, Object>();
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
					.map(e -> "El campo'" + e.getField() + "'" + e.getDefaultMessage()).collect(Collectors.toList());
			response.put("error_400", errores);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		if (categoriaActural == null) {
			response.put("error_404",
					"El producto, con id '".concat(id.toString()) + "' no se encontro en la base de datos");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		try {
			categoriaActural.setNombre(categoria.getNombre());
			nuevaCategoria = this.categoriaService.saveCategoria(categoriaActural);
		} catch (DataAccessException e) {
			response.put("error_500", "Error al actualizar el producto a la base de datos " + e.getLocalizedMessage()
					+ " " + e.getMostSpecificCause());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		response.put("categoria", nuevaCategoria);
		response.put("mensaje", "La categoria se actualizo con éxito");

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Metodo DELETE para eliminar la categoria
	@DeleteMapping("productos/categorias/delete/{id}")
	public ResponseEntity<?> deleteCategories(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<String, Object>();
		Categoria categoria = this.categoriaService.findCategoriaById(id);
		try {
			if (categoria == null) {
				response.put("error_404", "La categoria que desea eliminar con id '".concat(id.toString())
						.concat("' no se encotro en la base de datos"));
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			} else {
				this.categoriaService.deleteCategoria(categoria);
			}
		} catch (DataAccessException e) {
			response.put("error_500","No se puede eliminar ya que esta categoria tiene uno o mas productos asignados");
			return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La categoria con id " + id + " Se ha eliminado correctamente");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
