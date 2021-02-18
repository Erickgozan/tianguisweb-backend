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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianguisweb.api.model.entities.Cliente;
import com.tianguisweb.api.model.services.IClienteService;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api")
public class ClienteController {

	// Inyectar el servicio
	@Autowired
	private IClienteService clienteService;

	// Retornar lista de clientes
	@GetMapping("/clientes")
	public List<Cliente> ClientList() {
		return this.clienteService.findAll();
	}

	// GUARDAR AL CLIENTE
	@PostMapping("/clientes/create")
	public ResponseEntity<?> saveCustomer(@Valid @RequestBody Cliente cliente, BindingResult result) {

		Map<String, Object> response = new HashMap<String, Object>();
		Cliente nuevoCliente = null;

		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
					.map(e -> "El campo " + e.getField()+ " " + e.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("error_400", errores);

			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

		}

		try {
			nuevoCliente = this.clienteService.saveCliente(cliente);
			response.put("mensaje", "El cliente se agrego éxitosamente!");
			response.put("cliente", nuevoCliente);
		} catch (DataAccessException e) {
			response.put("error_500", "Error al guardar el cliente " + e.getLocalizedMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	//ACTUALIZAR EL CLIENTE
	@PutMapping("/clientes/update/{id}")
	public ResponseEntity<?> updateCustomer(@Valid @RequestBody Cliente cliente, 
			 BindingResult result, @PathVariable Long id){
		
		Cliente currentCustomer = this.clienteService.findClienteById(id);
		Cliente newCustomer=null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		//Evalar si el cliente existe
		if(currentCustomer==null) {		
			 response.put("error_404","El cliente no existe en la base de datos");
			 return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}
		
		//Evaluamos que los campos no esten vacios 
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
									.map(e -> "El campo: " + e.getField() + " " + e.getDefaultMessage())
									.collect(Collectors.toList());
			response.put("error_400",errores);
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		
		//Establecemos los nuevos valores al nevo cliente
		try {	
			currentCustomer.setNombre(cliente.getNombre());
			currentCustomer.setApellidoPaterno(cliente.getApellidoPaterno());
			currentCustomer.setApellidoMaterno(cliente.getApellidoMaterno());
			currentCustomer.setTelefono(cliente.getTelefono());
			currentCustomer.setDireccion(cliente.getDireccion());
			currentCustomer.setProductos(cliente.getProductos());
			currentCustomer.setFechaCompra(cliente.getFechaCompra());
			
			newCustomer = this.clienteService.saveCliente(currentCustomer);
			
		}catch(DataAccessException e) {
			response.put("error_500","Error al actualizar el cliente: " + e.getLocalizedMessage());
			return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El cliente se ha actializado con éxito");
		response.put("cliente",newCustomer);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
}
