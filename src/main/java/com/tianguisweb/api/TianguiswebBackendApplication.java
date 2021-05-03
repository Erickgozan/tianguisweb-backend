package com.tianguisweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tianguisweb.api.model.services.IUploadFileService;

@SpringBootApplication
public class TianguiswebBackendApplication implements CommandLineRunner {

	
	@Autowired
	private IUploadFileService uploadService;
	
	public static void main(String[] args) {
		SpringApplication.run(TianguiswebBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadService.delateAllFiles();		
	}
	
}
