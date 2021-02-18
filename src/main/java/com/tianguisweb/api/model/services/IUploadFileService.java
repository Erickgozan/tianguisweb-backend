package com.tianguisweb.api.model.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {

	// Subir el archivo
	public String uploadFile(MultipartFile nombreArchivo) throws IOException;

	// Evalua si el archivo existe
	public boolean isExist(String nombreArchivoAnterior);
	
	// Guardar multiples archivos
	//public void saveAll(List<MultipartFile> files) throws IOException;

	// Visualiza el archivo
	public Resource viewFile(String nombreArchivo) throws MalformedURLException;

	// Retorna la ruta absoluta del archivo
	public Path filePath(String nombreArchivo);

}
