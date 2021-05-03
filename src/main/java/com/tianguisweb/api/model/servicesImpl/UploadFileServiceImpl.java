package com.tianguisweb.api.model.servicesImpl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
//import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tianguisweb.api.model.services.IUploadFileService;

@Service
@Transactional
public class UploadFileServiceImpl implements IUploadFileService {

	private final Path rootFolder = Paths.get("uploads/products");
	
	//private final Path productFolder = Paths.get("products");	
	/*private final Path sliderFolder = Paths.get("slider");*/

	// Metodo para subir la imagen
	@Override
	public String uploadFile(MultipartFile nombreArchivo) throws IOException {

		// Genera un id a la imagen
		String filename = UUID.randomUUID().toString().concat("_").concat(nombreArchivo.getOriginalFilename())
				.replace(" ", "_");

		Path rutaArchivo = filePath(filename);

		if (!nombreArchivo.isEmpty()) {
			Files.copy(nombreArchivo.getInputStream(), rutaArchivo);
		}

		return filename;
	}

	// Metodo para subir varias imagenes
	@Override
	public List<String> saveAll(List<MultipartFile> files) throws IOException {
		List<String> imgs = new ArrayList<String>();
		for (MultipartFile file : files) {
			imgs.add(this.uploadFile(file));
		}
		return imgs;
	}


	// Metodo para eliminar la imagen del servidor, si esta ya existe
	@Override
	public boolean isExist(String nombreArchivoAnterior) {

		if (nombreArchivoAnterior != null) {
			Path rutaAnterior =this.rootFolder.resolve(nombreArchivoAnterior)
					.toAbsolutePath();
			File archivoAnterior = rutaAnterior.toFile();
			if (archivoAnterior.exists() && archivoAnterior.canRead()) {
				return archivoAnterior.delete();
			}
		}

		return false;
	}

	// Metodo para visualizar la imagen.
	@Override
	public Resource viewFile(String nombreArchivo) throws MalformedURLException {

		Path ruta = filePath(nombreArchivo);
		Resource recurso = new UrlResource(ruta.toUri());

		return recurso;
	}

	// Establecer la ruta del o los archivos
	@Override
	public Path filePath(String nombreArchivo) {

		File carpeta = this.rootFolder.toAbsolutePath().toFile();
		if (!carpeta.exists()) {
			carpeta.mkdirs();
		}
		return carpeta.toPath().resolve(nombreArchivo);
	}

	@Override
	public void delateAllFiles() {
		FileSystemUtils.deleteRecursively(this.rootFolder.toFile());
	}

}

/*
 * forma alternativa de agregar la ruta de user dir Path rutaArchivoAnterior =
 * Paths.get("uploads".concat(File.separator).concat("products"))
 * .toAbsolutePath().resolve(nombreArchivoAnterior).toAbsolutePath();
 * 
 * 
 * @Override
	public Stream<Path> loadAll() throws Exception {
		return null;
	}
 * 
 */
