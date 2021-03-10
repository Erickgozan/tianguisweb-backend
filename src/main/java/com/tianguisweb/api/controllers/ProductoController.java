package com.tianguisweb.api.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tianguisweb.api.model.entities.Categoria;
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

	// Listar productos
	@GetMapping("/productos")
	public List<Producto> productList() {
		return productoService.findAllProductos();
	}

	//Listar categorias
	@GetMapping("productos/categorias")
	public List<Categoria> categoriaList() {
		return productoService.finAllCategorias();
	}
	
	// Buscar un producto por su id
	@GetMapping("/productos/{id}")
	public ResponseEntity<?> productId(@PathVariable String id) {

		Map<String, Object> response = new HashMap<String, Object>();
		Producto producto = productoService.findProductoById(id);

		if (producto == null) {
			response.put("error_404",
					"El producto con id: '".concat(id.toString()).concat("' no se encontro en la base de datos"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(producto, HttpStatus.OK);
		}
	}

	// Guardar productos
	@PostMapping("/productos/create")
	public ResponseEntity<?> saveProduct(@Valid Producto producto, BindingResult result,
			@RequestPart MultipartFile file[]) {

		Map<String, Object> response = new HashMap<String, Object>();
		Producto nuevoProducto = null;
		String img1 = null, img2 = null, img3 = null, img4 = null, img5 = null;

		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors()
					.stream()
					.map(e -> "El campo '" + e.getField() + "' " + e.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("error_400", errores);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			// Guardar la ruta de las imagenes
			img1 = fileService.uploadFile(file[0]);
			img2 = fileService.uploadFile(file[1]);
			img3 = fileService.uploadFile(file[2]);
			img4 = fileService.uploadFile(file[3]);
			img5 = fileService.uploadFile(file[4]);
			// Establecer las rutas de las imagenes en el objeto producto
			producto.setImg1(img1);
			producto.setImg2(img2);
			producto.setImg3(img3);
			producto.setImg4(img4);
			producto.setImg5(img5);

		} catch (IOException e) {
			response.put("error_500", "Error al subir las imagenes" + e.getLocalizedMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			response.put("error_500", "Error al insertar el producto a la base de datos " + e.getLocalizedMessage()
					+ " " + e.getMostSpecificCause());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ArrayIndexOutOfBoundsException e) {
			if (img1 != null) {
				producto.setImg1(img1);
			} else {
				img1 = null;
			}
			if (img2 != null) {
				producto.setImg2(img2);
			} else {
				img2 = null;
			}
			if (img3 != null) {
				producto.setImg3(img3);
			} else {
				img3 = null;
			}
			if (img4 != null) {
				producto.setImg4(img4);
			} else {
				img4 = null;
			}
			if (img5 != null) {
				producto.setImg5(img5);
			} else {
				img5 = null;
			}
		}
		nuevoProducto = productoService.saveProducto(producto);
		response.put("mensaje", "El producto se agrego correctamente");
		response.put("producto", nuevoProducto);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Actualizar el producto
	@PutMapping("productos/update/{id}")
	public ResponseEntity<?> updateProduct(@Valid @RequestBody Producto producto, BindingResult result,
			@PathVariable String id) {

		Map<String, Object> response = new HashMap<String, Object>();
		Producto productoActual = productoService.findProductoById(id);
		Producto productoUpdate = null;

		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
					.map(e -> "El campo '" + e.getField() + "' " + e.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("error_400", errores);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		if (productoActual == null) {
			response.put("error_404",
					"El producto, con id '".concat(id.toString()) + "' no se encontro en la base de datos");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
			response.put("error_500", "Error al actualizar el producto a la base de datos " + e.getLocalizedMessage()
					+ " " + e.getMostSpecificCause());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		response.put("producto", productoUpdate);
		response.put("mensaje", "El producto se actualizo con éxito");

		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	// Ver imagen 
	@GetMapping("/uploads/img/{nombreArchivo:.+}")
	public ResponseEntity<Resource> viewImeges(@PathVariable String nombreArchivo) {

		Resource resource = null;
		try {
			resource = fileService.viewFile(nombreArchivo);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

		return new ResponseEntity<Resource>(resource, cabecera, HttpStatus.OK);
	}

	// Actualizar las imagenes
	//nota: Se usa el @RequestParam porque la petecion se recibe desde un form-data
	//y @RequestPart por que se piden archvios MultipartFile.
	@PutMapping("productos/image/update")
	public ResponseEntity<?> updateImageProduct(@RequestParam String id, @RequestPart MultipartFile file[]) {

		Producto productoActual = productoService.findProductoById(id);
		Producto fotoUpdate = null;
		Map<String, Object> response = new HashMap<String, Object>();
		String img1 = null, img2 = null, img3 = null, img4 = null, img5 = null;

		if (productoActual == null) {
			response.put("error_404",
					"El producto con id: '".concat(id.toString()).concat(" no se encontro en la base de datos"));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			// Guardar la ruta de las imagenes en la base de datos y las imagenes en el servidor
			img1 = fileService.uploadFile(file[0]);
			img2 = fileService.uploadFile(file[1]);
			img3 = fileService.uploadFile(file[2]);
			img4 = fileService.uploadFile(file[3]);
			img5 = fileService.uploadFile(file[4]);
			// Elinar las imagenes repetidas(por si existen)
			String fotoAnterior1 = productoActual.getImg1();
			String fotoAnterior2 = productoActual.getImg2();
			String fotoAnterior3 = productoActual.getImg3();
			String fotoAnterior4 = productoActual.getImg4();
			String fotoAnterior5 = productoActual.getImg5();
			fileService.isExist(fotoAnterior1);
			fileService.isExist(fotoAnterior2);
			fileService.isExist(fotoAnterior3);
			fileService.isExist(fotoAnterior4);
			fileService.isExist(fotoAnterior5);
			// Establecer las nuevas imagenes
			productoActual.setImg1(img1);
			productoActual.setImg2(img2);
			productoActual.setImg3(img3);
			productoActual.setImg4(img4);
			productoActual.setImg5(img5);

		} catch (IOException e) {
			response.put("error_500", "Error al subir el archivo " + e.getLocalizedMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ArrayIndexOutOfBoundsException e) {
				LOG.info("No hizo falta el switch");
				if (productoActual.getImg1() == null)
					productoActual.setImg1(img1);
				else if (productoActual.getImg2() == null)
					productoActual.setImg2(img1);
				else if (productoActual.getImg3() == null)
					productoActual.setImg3(img1);
				else if (productoActual.getImg4() == null)
					productoActual.setImg4(img1);
				else if (productoActual.getImg5() == null)
					productoActual.setImg5(img1);
		 }
		fotoUpdate = productoService.saveProducto(productoActual);
		response.put("mensaje", "La imagen se actualizó correctamente");
		response.put("producto", fotoUpdate);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
 
	// Eliminar la imagen
	@PutMapping("productos/image/delete/{id}")
	public ResponseEntity<?> deleteImgeProduct(@PathVariable String id, @RequestParam String img) {

		Producto productoActual = productoService.findProductoById(id);
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			//Si el producto no se encuentra
			if (productoActual == null) {
				response.put("error_404", "El producto que desea eliminar con id '".concat(id.toString())
						.concat("' no se encotro en la base de datos"));
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			} else {
				if (productoActual.getImg1().contentEquals(img)) {
					fileService.isExist(productoActual.getImg1());
					productoActual.setImg1(null);
				} else if (productoActual.getImg2().contentEquals(img)) {

					fileService.isExist(productoActual.getImg2());
					productoActual.setImg2(null);
				} else if (productoActual.getImg3().contentEquals(img)) {
					fileService.isExist(productoActual.getImg3());
					productoActual.setImg3(null);
				} else if (productoActual.getImg4().contentEquals(img)) {
					fileService.isExist(productoActual.getImg4());
					productoActual.setImg4(null);
				} else if (productoActual.getImg5().contentEquals(img)) {
					fileService.isExist(productoActual.getImg5());
					productoActual.setImg5(null);
				}
			}
		} catch (Exception e) {
			response.put("error_500", "La imagen que desea eliminar no existe: '".concat(img)
					.concat("', por favor verifique el nombre y vuelva a intentar"));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		productoService.saveProducto(productoActual);
		response.put("producto", productoActual);
		response.put("mensaje", "La imagen: '".concat(img).
				concat("' del producto: '" + productoActual.getNombre())
				.concat("'. se ha eliminado correctamente."));

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	// Eliminar el producto
	@DeleteMapping("productos/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable String id) {

		Map<String, Object> response = new HashMap<String, Object>();
		Producto producto = productoService.findProductoById(id);

		if (producto == null) {
			response.put("error_404", "El producto que desea eliminar con id '".concat(id.toString())
					.concat("' no se encotro en la base de datos"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			productoService.deleteProducto(producto);
			fileService.isExist(producto.getImg1());
			fileService.isExist(producto.getImg2());
			fileService.isExist(producto.getImg3());
			fileService.isExist(producto.getImg4());
			fileService.isExist(producto.getImg5());
		}

		response.put("mensaje", "El producto con id '".concat(id.toString()).concat("' se ha eliminado correctamente"));

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
