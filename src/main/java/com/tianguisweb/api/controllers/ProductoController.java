package com.tianguisweb.api.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tianguisweb.api.model.entities.Producto;
import com.tianguisweb.api.model.services.IProductoService;
import com.tianguisweb.api.model.services.IUploadFileService;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
@RequestMapping("/api")
public class ProductoController {

	private static final Logger LOG = LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private IProductoService productoService;

	@Autowired
	private IUploadFileService fileService;

	@GetMapping("/productos")
	public List<Producto> productList() {
		return productoService.findAllProductos();
	}

	@PostMapping("/productos")
	public ResponseEntity<Producto> saveProduct(@RequestParam MultipartFile file1, @RequestParam MultipartFile file2,
			@RequestParam MultipartFile file3, @RequestParam MultipartFile file4, @RequestParam MultipartFile file5,
			Producto producto) {
		
		Producto nuevoProducto = null;
		String img1 = null, img2 = null, img3 = null, img4 = null, img5 = null;

		try {
			//Guardar la ruta de las imagenes
			img1 = fileService.uploadFile(file1);
			img2 = fileService.uploadFile(file2);
			img3 = fileService.uploadFile(file3);
			img4 = fileService.uploadFile(file4);
			img5 = fileService.uploadFile(file5);
			//Establecer las rutas al objeto producto
			producto.setImg1(img1);
			producto.setImg2(img2);
			producto.setImg3(img3);
			producto.setImg4(img4);
			producto.setImg5(img5);

		} catch (IOException e) {
			LOG.error("Error al subir la imagen " + e.getMessage() + " : " + e.getCause());
		}
		
		if (producto != null) {
			nuevoProducto = productoService.saveProducto(producto);
		}

		return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
	}

	/*
	 * Map<String, Object> mensaje = new HashMap<String, Object>(); Producto
	 * productoNuevo = null;
	 * 
	 * if (result.hasErrors()) { List<String> errors =
	 * result.getFieldErrors().stream() .map(err -> "El campo" + err.getField() +
	 * " " + err.getDefaultMessage()) .collect(Collectors.toList());
	 * mensaje.put("errors", errors); return new ResponseEntity<Map<String,
	 * Object>>(mensaje, HttpStatus.BAD_REQUEST); }
	 * 
	 * try { productoNuevo = productoService.saveProducto(producto); } catch
	 * (DataAccessException e) { mensaje.put("mensaje",
	 * "Error al realizar el insert a la base de datos"); mensaje.put("error",
	 * e.getLocalizedMessage() + " " + e.getMostSpecificCause()); }
	 * 
	 * mensaje.put("producto", productoNuevo); mensaje.put("mensaje",
	 * "El producto se agrego con éxito");
	 * 
	 */

}
