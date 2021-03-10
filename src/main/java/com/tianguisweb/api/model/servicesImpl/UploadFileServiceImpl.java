package com.tianguisweb.api.model.servicesImpl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tianguisweb.api.model.services.IUploadFileService;

@Service
public class UploadFileServiceImpl implements IUploadFileService {
	
	//private static final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
	
	//Metodo para subir la imagen
	@Override
	public String uploadFile(MultipartFile nombreArchivo) throws IOException {
		
		//Genera un id a la imagen 
		String filename = UUID.randomUUID().toString().concat("_").concat(nombreArchivo.getOriginalFilename())
				.replace(" ", "_");
		
		Path rutaArchivo = filePath(filename);

		if (!nombreArchivo.isEmpty()) {
			Files.copy(nombreArchivo.getInputStream(), rutaArchivo);
		}
		
		return filename;
	}
	
	//Metodo para eliminar la imagen del servidor, si esta ya existe 
	@Override
	public boolean isExist(String nombreArchivoAnterior) {

		if (nombreArchivoAnterior != null) {
			Path rutaAnterior = Paths.get("uploads/products").resolve(nombreArchivoAnterior).toAbsolutePath();
			File archivoAnterior = rutaAnterior.toFile();
			if (archivoAnterior.exists() && archivoAnterior.canRead()) {
				return archivoAnterior.delete();
			}
		}
		
		return false;
	}
	
	//Metodo para visualizar la imagen.
	@Override
	public Resource viewFile(String nombreArchivo) throws MalformedURLException {

		Path ruta = filePath(nombreArchivo);
		Resource recurso = new UrlResource(ruta.toUri());

		if (!recurso.exists() && !recurso.isReadable()) {
			ruta = Paths.get("src/main/resources/static/images").resolve("no-user.png").toAbsolutePath();
			recurso = new UrlResource(ruta.toUri());
		}

		return recurso;
	}

	//Establecer la ruta del o los archivos
	@Override
	public Path filePath(String nombreArchivo) {
		
		File carpeta = Paths.get("uploads/products").toAbsolutePath().toFile();
		
		if (!carpeta.exists()) {
			carpeta.mkdir();
		}	
		
		return carpeta.toPath().resolve(nombreArchivo);
	}
}





/*forma alternativa de agregar la ruta de user dir 
 * Path rutaArchivoAnterior = Paths.get("uploads".concat(File.separator).concat("products"))
 * .toAbsolutePath().resolve(nombreArchivoAnterior).toAbsolutePath();
 * 
 * 	/*@Override
 /*Metodo alternativo para subir un arreglo de imagenes
public void saveAll(List<MultipartFile> files) throws IOException {
	// TODO Auto-generated method stub
	for (MultipartFile file : files) {
	     this.uploadFile(file);
	}
}*/	

