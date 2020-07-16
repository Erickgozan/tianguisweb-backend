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

	// private static final Logger LOG =
	// LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private IProductoService productoService;

	@Autowired
	private IUploadFileService fileService;

	// Listar productos
	@GetMapping
	public List<Producto> productList() {
		return productoService.findAllProductos();
	}
	
	
	@GetMapping("{id}")
	public ResponseEntity<?> productId(@PathVariable Long id){
		
		Map<String,Object> response = new HashMap<String, Object>();
		Producto producto = productoService.findProductoById(id);
		
		if(producto==null) {
			response.put("error", "El producto con id: '"
					.concat(id.toString()).concat("' no se encontro en la base de datos"));			
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(producto,HttpStatus.OK);
		}
	}
	
	// guardar productos
	@PostMapping("/save")
	public ResponseEntity<?> saveProduct(@Valid Producto producto, BindingResult result,
			@RequestParam MultipartFile file[]) {

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
			img1 = fileService.uploadFile(file[0]);
			img2 = fileService.uploadFile(file[1]);
			img3 = fileService.uploadFile(file[2]);
			img4 = fileService.uploadFile(file[3]);
			img5 = fileService.uploadFile(file[4]);
			// Establecer las rutas al objeto producto
			producto.setImg1(img1);
			producto.setImg2(img2);
			producto.setImg3(img3);
			producto.setImg4(img4);
			producto.setImg5(img5);

			nuevoProducto = productoService.saveProducto(producto);

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

	// Actualizar el producto
	@PutMapping("update/{id}")
	public ResponseEntity<?> updateProduct(@Valid @RequestBody Producto producto, BindingResult result,
			@PathVariable Long id) {

		Map<String, Object> response = new HashMap<String, Object>();
		Producto productoActual = productoService.findProductoById(id);
		Producto productoUpdate = null;

		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
					.map(e -> "El campo '" + e.getField() + "' " + e.getDefaultMessage()).collect(Collectors.toList());
			response.put("error", errores);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		if (productoActual == null) {
			response.put("error", "El producto, con id '".concat(id.toString()) + "' no se encontro en la base de datos");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		try {

			productoActual.setNombre(producto.getNombre());
			productoActual.setPrecio(producto.getPrecio());
			productoActual.setDescripcion(producto.getDescripcion());
			productoActual.setCaracteristicas(producto.getCaracteristicas());
			productoActual.setStock(producto.getStock());
			productoActual.setOferta(producto.getOferta());
			productoActual.setCategoria(producto.getCategoria());

			productoUpdate = productoService.saveProducto(productoActual);

		} catch (DataAccessException e) {
			response.put("error", "Error al actualizar el producto a la base de datos " + e.getLocalizedMessage() + " "
					+ e.getMostSpecificCause());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		response.put("mensaje", "El producto se actualizo con éxito");
		response.put("producto", productoUpdate);

		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	// Actualizar las imagenes
	@PutMapping("image/update")
	public ResponseEntity<?> updateImage(@RequestParam Long id, @RequestParam MultipartFile file[]) {

		Producto fotoActual = productoService.findProductoById(id);
		Producto fotoUpdate = null;
		Map<String, Object> response = new HashMap<String, Object>();
		String img1 = null, img2 = null, img3 = null, img4 = null, img5 = null;

		if (fotoActual == null) {
			response.put("error",
					"El producto con id: '".concat(id.toString()).concat(" no se encontro en la base de datos"));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			// Guardar la ruta de las imagenes
			img1 = fileService.uploadFile(file[0]);
			img2 = fileService.uploadFile(file[1]);
			img3 = fileService.uploadFile(file[2]);
			img4 = fileService.uploadFile(file[3]);
			img5 = fileService.uploadFile(file[4]);
		} catch (IOException e) {
			response.put("error", "Error al subir el archivo " + e.getLocalizedMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Elinar las imagenes repetidas
		String foto1Anterior = fotoActual.getImg1();
		String foto2Anterior = fotoActual.getImg2();
		String foto3Anterior = fotoActual.getImg3();
		String foto4Anterior = fotoActual.getImg4();
		String foto5Anterior = fotoActual.getImg5();
		fileService.isExist(foto1Anterior);
		fileService.isExist(foto2Anterior);
		fileService.isExist(foto3Anterior);
		fileService.isExist(foto4Anterior);
		fileService.isExist(foto5Anterior);

		// Establecer las nuevas imagenes
		fotoActual.setImg1(img1);
		fotoActual.setImg2(img2);
		fotoActual.setImg3(img3);
		fotoActual.setImg4(img4);
		fotoActual.setImg5(img5);
		fotoUpdate = productoService.saveProducto(fotoActual);
		response.put("mensaje", "Las imagenes se actualizaron correctamente");
		response.put("foto", fotoUpdate);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	//Eliminar el producto
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id){
		
		Map<String,Object> response = new HashMap<String, Object>();
		Producto producto = productoService.findProductoById(id);
		
		if(producto==null) {
			response.put("error", "El producto que desea eliminar con id '".
					concat(id.toString()).concat("' no se encotro en la base de datos"));
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}else {		
			productoService.deleteProducto(producto);
			fileService.isExist(producto.getImg1());
			fileService.isExist(producto.getImg2());
			fileService.isExist(producto.getImg3());
			fileService.isExist(producto.getImg4());
			fileService.isExist(producto.getImg5());			
		}		
		
		response.put("mensaje", "El producto con id '".concat(id.toString())
				.concat("' se ha eliminado correctamente"));
		
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}

}
