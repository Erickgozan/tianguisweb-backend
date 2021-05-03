package com.tianguisweb.api.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tianguisweb.api.model.entities.Slider;
import com.tianguisweb.api.model.services.*;

@RestController
@CrossOrigin(value = { "http://localhost:4200" })
@RequestMapping("/api")
public class SliderController {

	//private final Logger logger = LoggerFactory.getLogger(SliderController.class);

	private final ISliderService sliderService;
	private final IUploadFileService fileService;

	@Autowired
	public SliderController(ISliderService sliderService, IUploadFileService fileService){
		this.fileService = fileService;
		this.sliderService =sliderService;
	}

	@GetMapping("/slider")
	public List<Slider> listSlider(){
		return this.sliderService.listAllSlider();
	}
	
	@GetMapping("/slider/{id}")
	public ResponseEntity<?> listSliderById(@PathVariable String id){
			
		Map<String,Object> response = new HashMap<>();
		Slider slider = this.sliderService.findSliderById(id);
		
		if(slider==null) {
			response.put("error_404", "No se encontro el slider con el id: " + id);
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(slider,HttpStatus.OK);
	}

	// Crear el slider
	@Secured("ROLE_ADMIN")
	@PostMapping("/slider/create")
	public ResponseEntity<?> saveSlider(Slider slider, @RequestPart("files") List<MultipartFile> files) {

		Map<String, Object> response = new HashMap<>();
		Slider newSlider;
		List<String> imgs = new ArrayList<>();
		String img1 = "", img2 = "", img3;

		try {
			// guardando el arreglo de nombres
			imgs.addAll(fileService.saveAll(files));

			img1 = imgs.get(0);
			img2 = imgs.get(1);
			img3 = imgs.get(2);

			slider.setImg1(img1);
			slider.setImg2(img2);
			slider.setImg3(img3);

		} catch (IOException e) {
			response.put("error_500", "Error al subir las imagenes" + e.getLocalizedMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DataAccessException e) {
			response.put("error_500", "Error al insertar el slider a la base de datos");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IndexOutOfBoundsException e) {
			this.fileService.isExist(img1);
			this.fileService.isExist(img2);
			response.put("error_500", "Se requieren todas las imagenes");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		newSlider = this.sliderService.saveSlider(slider);
		response.put("mensaje", "El slider se agrego con éxito");
		response.put("slider", newSlider);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Actualizar la/las imagenes
	@Secured("ROLE_ADMIN")
	@PutMapping("/slider/image/update")
	public ResponseEntity<?> updateSlider(@RequestParam String id, @RequestPart("files") List<MultipartFile> files) {

		Map<String, Object> response = new HashMap<>();
		Slider sliderActual = this.sliderService.findSliderById(id);
		Slider sliderUpdate;
		List<String> imgs = new ArrayList<>();

		if (sliderActual == null) {
			response.put("error_404",
					"El slider con id: '".concat(id).concat(" no se encontro en la base de datos"));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			imgs.addAll(fileService.saveAll(files));
			
			if (sliderActual.getImg1().equals("")) {
				sliderActual.setImg1(imgs.get(0));
			}	
			else if (sliderActual.getImg2().equals("")) {
				sliderActual.setImg2(imgs.get(0));
			}			
			else if (sliderActual.getImg3().equals("")) {
				sliderActual.setImg3(imgs.get(0));
			}

		} catch (IOException e) {
			response.put("error_500", "Error al subir las imagenes" + e.getLocalizedMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		
		sliderUpdate = this.sliderService.saveSlider(sliderActual);
		response.put("mensaje", "Se actualizó con éxito el slider");
		response.put("slider", sliderUpdate);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Eliminar la/las imagenes
	@Secured("ROLE_ADMIN")
	@PutMapping("/slider/image/delete/{id}")
	public ResponseEntity<?> deleteImgStock(@PathVariable String id, @RequestParam String img) {

		Slider sliderActual = this.sliderService.findSliderById(id);
		Map<String, Object> response = new HashMap<>();

		try {
			// Si el producto no se encuentra
			if (sliderActual == null) {
				response.put("error_404", "El Stock que desea eliminar con id '".concat(id)
						.concat("' no se encotro en la base de datos"));
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			} else {
				if (sliderActual.getImg1().equals(img)) {
					fileService.isExist(sliderActual.getImg1());
					sliderActual.setImg1("");
				} else if (sliderActual.getImg2().equals(img)) {

					fileService.isExist(sliderActual.getImg2());
					sliderActual.setImg2("");
				} else if (sliderActual.getImg3().equals(img)) {

					fileService.isExist(sliderActual.getImg3());
					sliderActual.setImg3("");
				}

			}
		} catch (Exception e) {
			response.put("error_500",
					"La imagen que desea eliminar no existe: '".concat(img)
							.concat("', por favor verifique el nombre y vuelva a intentar").concat("Error de tipo: ")
							.concat(e.getLocalizedMessage()));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		this.sliderService.saveSlider(sliderActual);
		response.put("producto", sliderActual);
		response.put("mensaje", "La imagen: '".concat(img).concat("'. se ha eliminado correctamente."));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Visualizar las imagen
	@GetMapping("/view/img/{imgName:.+}")
	public ResponseEntity<Resource> vewImg(@PathVariable String imgName) {
		Resource resource = null;
		try {
			resource = fileService.viewFile(imgName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		HttpHeaders cabecera = new HttpHeaders();
		assert resource != null;
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

		return new ResponseEntity<>(resource, cabecera, HttpStatus.OK);
	}
}
