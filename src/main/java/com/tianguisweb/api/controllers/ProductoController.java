package com.tianguisweb.api.controllers;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tianguisweb.api.model.entities.Producto;
import com.tianguisweb.api.model.services.IProductoService;
import com.tianguisweb.api.model.services.IUploadFileService;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
@RequestMapping("/productos")
public class ProductoController {

	//private static final Logger LOG = LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private IProductoService productoService;

	@Autowired
	private IUploadFileService fileService;
	
	//Listar productos
	@GetMapping
	public List<Producto> productList() {
		return productoService.findAllProductos();
	}
	
	//guardar productos
	@PostMapping("/save")
	public ResponseEntity<?> saveProduct(@Valid Producto producto, BindingResult result,
			@RequestParam MultipartFile file1, @RequestParam MultipartFile file2, @RequestParam MultipartFile file3,
			@RequestParam MultipartFile file4, @RequestParam MultipartFile file5) {

		Map<String, Object> response = new HashMap<String, Object>();
		Producto nuevoProducto = null;
		String img1 = null, img2 = null, img3 = null, img4 = null, img5 = null;
		
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
					.map(e -> "El campo '" + e.getField() + "' " + e.getDefaultMessage()).collect(Collectors.toList());
			response.put("error", errores);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			// Guardar la ruta de las imagenes
			img1 = fileService.uploadFile(file1);
			img2 = fileService.uploadFile(file2);
			img3 = fileService.uploadFile(file3);
			img4 = fileService.uploadFile(file4);
			img5 = fileService.uploadFile(file5);
			// Establecer las rutas al objeto producto
			producto.setImg1(img1);
			producto.setImg2(img2);
			producto.setImg3(img3);
			producto.setImg4(img4);
			producto.setImg5(img5);

			if (producto != null && producto instanceof Producto) {
				nuevoProducto = productoService.saveProducto(producto);
			}

		} catch (IOException e) {
			response.put("error", "Error al subir las imagenes" + e.getLocalizedMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			response.put("error", "Error al insertar el producto a la base de datos " + e.getLocalizedMessage() + " "
					+ e.getMostSpecificCause());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El producto se agrego correctamente");
		response.put("producto", nuevoProducto);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	
}
