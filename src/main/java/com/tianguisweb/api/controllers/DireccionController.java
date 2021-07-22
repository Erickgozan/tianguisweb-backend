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
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianguisweb.api.model.entities.Direccion;
import com.tianguisweb.api.model.services.IDireccionService;

@CrossOrigin(origins = {"http://localhost:4200","https://api-sepomex.hckdrk.mx"})
@RequestMapping(path = "/api")
@RestController
public class DireccionController {

	@Autowired
	private IDireccionService direccionService;

	// Listar las direcciones
	@Secured("ROLE_ADMIN")
	@GetMapping("/direcciones")
	public List<Direccion> findAllDirecciones() {
		return direccionService.findAllDirecciones();
	}

	// Buscar la direccion por id
	@Secured("ROLE_ADMIN")	
	@GetMapping("/direcciones/{id}")
	public ResponseEntity<?> findDireccionById(@PathVariable String id) {

		Map<String, Object> response = new HashMap<String, Object>();
		Direccion direccion = this.direccionService.findDireccionById(id);

		if (direccion == null) {
			response.put("error_404", "La dirección no se encontro en la base de datos");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

	}

	// Guardad las direcciones
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@PostMapping("/direcciones/save")
	public ResponseEntity<?> saveDireccion(@Valid @RequestBody Direccion direccion, BindingResult result) {

		Map<String, Object> response = new HashMap<String, Object>();
		Direccion newAdress;

		if (result.hasErrors()) {

			List<String> errores = result.getFieldErrors().stream()
					.map(e -> "El campo '" + e.getField() + "' " + e.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("error_400", errores);

			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {

			newAdress = this.direccionService.saveDireccion(direccion);

		} catch (DataAccessException e) {

			response.put("error_500", "Hubo un error en el servidor: " + e.getLocalizedMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La direccion se agrego con éxito");
		response.put("direccion", newAdress);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Actualizar direcciones
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@PutMapping("/direcciones/update/{id}")
	public ResponseEntity<?> updateDireccion(@Valid @RequestBody Direccion direccion, @PathVariable String id, 
			BindingResult result) {

		Map<String, Object> response = new HashMap<String, Object>();
		Direccion direccionActual = this.direccionService.findDireccionById(id);
		Direccion direccionUpdate;

		if (direccionActual == null) {
			response.put("error_404", "La direccion no se encontro en la base de datos");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		if (result.hasErrors()) {

			List<String> errores = result.getFieldErrors().stream()
					.map(e -> "El campo '" + e.getField() + "' " + e.getDefaultMessage()).collect(Collectors.toList());

			response.put("error_400", errores);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			direccionActual.setCalle(direccion.getCalle());
			direccionActual.setColonia(direccion.getColonia());
			direccionActual.setCp(direccion.getCp());
			direccionActual.setEstado(direccion.getEstado());
			direccionActual.setMunicipio(direccion.getMunicipio());
			direccionActual.setNoExterior(direccion.getNoExterior());
			direccionActual.setNoInterior(direccion.getNoInterior());

			direccionUpdate = this.direccionService.saveDireccion(direccionActual);

		} catch (DataAccessException e) {

			response.put("error_500", "Hubo un error en el servidor: " + e.getLocalizedMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		response.put("mensaje", "La dirección se actualizo con éxito! ");
		response.put("direccion", direccionUpdate);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Eliminar direccion
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/direcciones/delete")
	public ResponseEntity<?> deleteDireccione(@RequestBody Direccion direccion) {

		Direccion direccionActual = this.direccionService.findDireccionById(direccion.getId());
		Map<String, Object> response = new HashMap<String, Object>();

		if (direccionActual == null) {
			response.put("error_404", "La dirección no se encontro en la base de datos");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			this.direccionService.delateDireccion(direccionActual);
			response.put("mensaje", "La direccón se elimino con éxito");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

	}

}
