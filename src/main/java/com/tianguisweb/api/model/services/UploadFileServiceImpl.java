package com.tianguisweb.api.model.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {
	
	private static final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);

	@Override
	public String uploadFile(MultipartFile nombreArchivo) throws IOException {

		String filename = UUID.randomUUID().toString().concat("_").concat(nombreArchivo.getOriginalFilename())
				.replace(" ", "_");
		Path ruta = filePath(filename);

		if (!nombreArchivo.isEmpty()) {
			Files.copy(nombreArchivo.getInputStream(), ruta);
		}else {
			filename = null;
		}

		return filename;
	}

	@Override
	public boolean isExist(String nombreArchivoAnterior) {

		if (nombreArchivoAnterior != null) {
			StringBuilder rutaAnterior = new StringBuilder();
			rutaAnterior.append(System.getProperty("user.dir"));
			rutaAnterior.append(File.separator);
			rutaAnterior.append("uploads".concat(File.separator).concat("products"));
			rutaAnterior.append(File.separator);
			rutaAnterior.append(nombreArchivoAnterior);	
			File archivoAnterior = Paths.get(rutaAnterior.toString()).toFile();
			if (archivoAnterior.exists() && archivoAnterior.canRead()) {
				return archivoAnterior.delete();
			}
		}

		return false;
	}

	@Override
	public Resource viewFile(String nombreArchivo) throws MalformedURLException {

		Path ruta = filePath(nombreArchivo);
		Resource recurso = new UrlResource(ruta.toUri());

		if (!recurso.exists() && !recurso.isReadable()) {
			recurso = new UrlResource(ruta.toUri());
		}

		return null;
	}

	@Override
	public Path filePath(String nombreArchivo) {
		
		StringBuilder ruta = new StringBuilder();
		ruta.append(System.getProperty("user.dir"));
		ruta.append(File.separator);
		ruta.append("uploads".concat(File.separator).concat("products"));
		
		log.info("La ruta es "+ruta.toString());
		
		File carpeta = Paths.get(ruta.toString()).toAbsolutePath().toFile();
		if (!carpeta.exists()) {
			carpeta.mkdir();
		}
		
		ruta.append(File.separator);
		ruta.append(nombreArchivo);
		
		return carpeta.toPath().resolve(nombreArchivo);
	}
	
	
	/*forma alternativa de agregar la ruta de user dir 
	 * Path rutaArchivoAnterior = Paths.get("uploads".concat(File.separator).concat("products"))
	 * .toAbsolutePath().resolve(nombreArchivoAnterior).toAbsolutePath();	
	 * */

}
