package com.tianguisweb.api.model.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;
//import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {

	// Subir el archivo
	public String uploadFile(MultipartFile nombreArchivo) throws IOException;

	// Evalua si el archivo existe
	public boolean isExist(String nombreArchivoAnterior);
	
	// Guardar multiples archivos
	public List<String> saveAll(List<MultipartFile> files) throws IOException;
	
	//Vidaulizar todos los archivos
	//public Stream<Path> loadAll() throws Exception;
	
	// Visualiza el archivo
	public Resource viewFile(String nombreArchivo) throws MalformedURLException;

	// Retorna la ruta absoluta del archivo
	public Path filePath(String nombreArchivo);
	
	//Metodos para eliminar los archivos *Solo aplicar en desarrollo
	public void delateAllFiles();
	


}
