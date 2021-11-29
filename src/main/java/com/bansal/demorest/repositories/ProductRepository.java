package com.bansal.demorest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.bansal.demorest.domain.Product; 

public interface ProductRepository extends JpaRepository<Product, Long>  {

	List<Product> findByName(String name);
	
}
