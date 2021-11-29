package com.bansal.demorest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bansal.demorest.domain.Product;
import com.bansal.demorest.service.ProductService;

@RestController
public class HomeController {


	@Autowired
	ProductService productService;

	@GetMapping(value = "/getAllProducts", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<Product> getProduct(@RequestHeader(required = false) int pageNo, 
			@RequestHeader(required = false) int pageSize, 
			@RequestParam (required = false) long id, 
			@RequestParam (required = false) String name) {
		return productService.getProducts(pageNo, pageSize);
	}

	@GetMapping("/product/{id}")  
	private Product getProduct(@PathVariable("id") long id)   
	{  
		return productService.getProductById(id);  
	} 
	
	
	@GetMapping("/getProductByName/{name}")  
	private List<Product> getProductByName(@PathVariable("name") String name)   
	{  
		return productService.getProductByName(name);  
	} 

	@DeleteMapping("/product/{id}")  
	private void deleteProduct(@PathVariable("id") long id)   
	{  
		productService.delete(id);  
	}  

	@PostMapping("/saveProduct")  
	private ResponseEntity<String> saveProduct(@RequestBody Product product)   
	{  
		ResponseEntity<String> responseEntity = null;
		try {
			productService.saveOrUpdate(product);
			responseEntity = new ResponseEntity<String>(product.getId()+"", HttpStatus.OK);
		}
		catch(IllegalArgumentException iae) {
			responseEntity = new ResponseEntity<String>(iae.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return responseEntity;  
	} 
	
	
	@GetMapping(value = "/getProductByIdAndName", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<Product> getProductByIdAndName(@RequestParam (required = false) long id, 
			@RequestParam (required = false) String name) {
		return productService.findProductByAuthorIdAndName(id, name);
	}



}
