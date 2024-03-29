package com.tianguisweb.api.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tianguisweb.api.model.entities.*;
import com.tianguisweb.api.model.services.*;

@RestController
@CrossOrigin(origins = { "http://localhost:4200"})
@RequestMapping("/api")
public class ProductoController {

	private final Logger LOG = LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private IProductoService productoService;

	@Autowired
	private IUploadFileService fileService;

	// Listar productos
	@GetMapping("/productos")
	public List<Producto> productList() {
		return productoService.findAllProductos();
	}

    // Listar productos paginados
    @GetMapping("/productos/page/{page}")
    public Page<Producto> productList(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page,6);
        return this.productoService.findAllPageProductos(pageable);
    }

    //Crear metodo que me liste productos por nombre,categoria y oferta

    //Listar de productos por nombre
	@GetMapping(value = "/productos/buscar/nombre",params = "nombre")
	@ResponseStatus(HttpStatus.OK)
	public List<Producto> findProductosByNombre(@RequestParam String nombre){
		return this.productoService.findAllProductsByNombre(nombre);
	}

	//Listar de productos por nombre
	@GetMapping(value = "/productos/buscar/datos",params = "datos")
	@ResponseStatus(HttpStatus.OK)
	public List<Producto> findProductosByDatos(@RequestParam String datos){
		return this.productoService.findAllProductosByDatos(datos);
	}

    //Listar de productos por categoria
	@GetMapping(value = "/productos/buscar/categoria")
	@ResponseStatus(HttpStatus.OK)
    public List<Producto> findProductosByCategoria(Categoria categoria){
	    return  this.productoService.findProductosByCategoria(categoria );
    }

    //Lista de producto por oferta
    @GetMapping(value = "/productos/buscar/oferta",params = "oferta")
	@ResponseStatus(HttpStatus.OK)
	public List<Producto> findProductosByOferta(@RequestParam Boolean oferta){
		return this.productoService.findProductosByOferta(oferta);
	}

    //Listar categorias
	@GetMapping("/productos/categorias")
	public List<Categoria> categoriaList() {
		return productoService.finAllCategorias();
	}


	// Buscar un producto por su id
	@GetMapping("/productos/{id}")
	public ResponseEntity<?> productId(@PathVariable String id) {

		Map<String, Object> response = new HashMap<>();
		Producto producto = productoService.findProductoById(id);

		if (producto == null) {
			response.put("error_404",
					"El producto con id: '".concat(id).concat("' no se encontro en la base de datos"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(producto, HttpStatus.OK);
		}
	}
	
	// Guardar productos
	@Secured("ROLE_ADMIN")
	@PostMapping("/productos/create")
	public ResponseEntity<?> saveProduct(@Valid Producto producto, BindingResult result,
			@RequestPart MultipartFile[] file) {

		Map<String, Object> response = new HashMap<>();
		Producto nuevoProducto;
		String img1 = "", img2 = "", img3 = "", img4 = "", img5 = "";

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
			if (!img1.equals("")) {
				producto.setImg1(img1);
			} else {
				producto.setImg1(""); 
			}
			if (!img2.equals("")) {
				producto.setImg2(img2);
			} else {
				producto.setImg2("");
			}
			if (!img3.equals("")) {
				producto.setImg3(img3);
			} else {
				producto.setImg3("");
			}
			if (!img4.equals("")) {
				producto.setImg4(img4);
			} else {
				producto.setImg4("");
			}
			if (!img5.equals("")) {
				producto.setImg5(img5);
			} else {
				producto.setImg5("");
			}
		}
		nuevoProducto = productoService.saveProducto(producto);
		response.put("mensaje", "El producto se agrego correctamente");
		response.put("producto", nuevoProducto);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Actualizar el producto
	@Secured("ROLE_ADMIN")
	@PutMapping("/productos/update/{id}")
	public ResponseEntity<?> updateProduct(@Valid @RequestBody Producto producto, BindingResult result,
			@PathVariable String id) {

		Map<String, Object> response = new HashMap<>();
		Producto productoActual = productoService.findProductoById(id);
		Producto productoUpdate;

		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
					.map(e -> "El campo '" + e.getField() + "' " + e.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("error_400", errores);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		if (productoActual == null) {
			response.put("error_404",
					"El producto, con id '".concat(id) + "' no se encontro en la base de datos");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		try {

			productoActual.setNombre(producto.getNombre());
			productoActual.setPrecio(producto.getPrecio());
			productoActual.setPrecioOriginal(producto.getPrecioOriginal());
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
		response.put("message", "El producto se actualizo con éxito");

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
		assert resource != null;
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

		return new ResponseEntity<>(resource, cabecera, HttpStatus.OK);
	}

	// Actualizar las imagenes
	//nota: Se usa el @RequestParam porque la petecion se recibe desde un form-data
	//y @RequestPart por que se piden archvios MultipartFile.
	@Secured("ROLE_ADMIN")
	@PutMapping("/productos/image/update")
	public ResponseEntity<?> updateImageProduct(@RequestParam String id, @RequestPart MultipartFile[] file) {

		Producto productoActual = productoService.findProductoById(id);
		Producto fotoUpdate;
		Map<String, Object> response = new HashMap<>();
		String img1 = null, img2, img3, img4, img5;

		if (productoActual == null) {
			response.put("error_404",
					"El producto con id: '".concat(id).concat(" no se encontro en la base de datos"));
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
			fileService.isExist(productoActual.getImg1());
			fileService.isExist(productoActual.getImg2());
			fileService.isExist(productoActual.getImg3());
			fileService.isExist(productoActual.getImg4());
			fileService.isExist(productoActual.getImg5());
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
	//La razon de img1 en todos los set es porque todos son indice 0 en actualizar ya que se agregan uno por uno
			if (productoActual.getImg1().equals(""))
					productoActual.setImg1(img1);
				else if (productoActual.getImg2().equals(""))
					productoActual.setImg2(img1);
				else if (productoActual.getImg3().equals(""))
					productoActual.setImg3(img1);
				else if (productoActual.getImg4().equals(""))
					productoActual.setImg4(img1);
				else if (productoActual.getImg5().equals(""))
					productoActual.setImg5(img1);
		 }
		fotoUpdate = productoService.saveProducto(productoActual);
		response.put("mensaje", "La imagen se actualizó correctamente");
		response.put("producto", fotoUpdate);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
 
	// Eliminar la imagen
	@Secured("ROLE_ADMIN")
	@PutMapping("/productos/image/delete/{id}")
	public ResponseEntity<?> deleteImgeProduct(@PathVariable String id, @RequestParam String img) {

		Producto productoActual = productoService.findProductoById(id);
		Map<String, Object> response = new HashMap<>();
		
		try {
			//Si el producto no se encuentra
			if (productoActual == null) {
				response.put("error_404", "El producto que desea eliminar con id '".concat(id)
						.concat("' no se encotro en la base de datos"));
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			} else {
				if (productoActual.getImg1().equals(img)) {
					fileService.isExist(productoActual.getImg1());
					productoActual.setImg1("");
				} else if (productoActual.getImg2().equals(img)) {
					fileService.isExist(productoActual.getImg2());
					productoActual.setImg2("");
				} else if (productoActual.getImg3().equals(img)) {
					fileService.isExist(productoActual.getImg3());
					productoActual.setImg3("");
				} else if (productoActual.getImg4().equals(img)) {
					fileService.isExist(productoActual.getImg4());
					productoActual.setImg4("");
				} else if (productoActual.getImg5().equals(img)) {
					fileService.isExist(productoActual.getImg5());
					productoActual.setImg5("");
				}
				
				LOG.info("Item 1 " + productoActual.getImg1()+
						"Item 2 "+ productoActual.getImg2() + "Item 3 " + productoActual.getImg3()
						+ "Item 4 "+ productoActual.getImg4() + "Item 5 "+ productoActual.getImg5());
			}
		} catch (Exception e) {
			response.put("error_500", "La imagen que desea eliminar no existe: '".concat(img)
					.concat("', por favor verifique el nombre y vuelva a intentar")
					.concat("Error de tipo: ")
					.concat(e.getLocalizedMessage()));
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
	@Secured("ROLE_ADMIN")
	@DeleteMapping("productos/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable String id) {

		Map<String, Object> response = new HashMap<>();
		Producto producto = productoService.findProductoById(id);

		if (producto == null) {
			response.put("error_404", "El producto que desea eliminar con id '".concat(id)
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

		response.put("mensaje", "El producto con id '".concat(id).concat("' se ha eliminado correctamente"));

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
