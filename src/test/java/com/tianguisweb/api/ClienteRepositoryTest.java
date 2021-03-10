package com.tianguisweb.api;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//import com.tianguisweb.api.model.entities.Producto;
//import com.tianguisweb.api.model.services.IClienteService;

@DataJpaTest
public class ClienteRepositoryTest {
	
	@Autowired
	//private IClienteService clienteService;
	
	@Test
	private void whenFindByCategory_thenResultListProduct() {
		
		
	}

}
