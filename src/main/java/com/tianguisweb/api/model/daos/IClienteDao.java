package com.tianguisweb.api.model.daos;

import com.tianguisweb.api.model.entities.Usuario;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tianguisweb.api.model.entities.Cliente;

@Repository
public interface IClienteDao extends JpaRepository<Cliente, String>{
			
	Cliente findByEmail(String email);

	@Modifying
	@Query("UPDATE Usuario u SET u.password = :password WHERE u.id = :id")
	void updateUsuarioPassword(String password, String id);
}
