package com.bansal.demorest.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bansal.demorest.domain.Product;
import com.bansal.demorest.repositories.ProductRepository;

@Service
public class ProductService {


	@Autowired
	ProductRepository productRepository;

	public List<Product> getProducts(int pageNo, int pageSize){
		List<Product> products = new ArrayList<Product>();  
		pageNo = pageNo < 0 ? 1 : pageNo;
		pageSize = pageSize <=0 ? 3 : pageSize;
		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
		productRepository.findAll(pageRequest).forEach(prouduct -> products.add(prouduct));  
		return products;  
	}


	public List<Product> getProducts(int pageNo, int pageSize, long id, String name){
		List<Product> products = new ArrayList<Product>();  
		pageNo = pageNo < 0 ? 1 : pageNo;
		pageSize = pageSize <=0 ? 3 : pageSize;
		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
		productRepository.findAll(pageRequest).forEach(prouduct -> products.add(prouduct));  
		return products;  
	}

	//getting a specific record  
	public Product getProductById(long id)   
	{  
		return productRepository.findById(id).get();  
	}  


	//getting a specific record  
	public List<Product> getProductByName(String name)   
	{  
		return productRepository.findByName(name);  
	} 

	public void saveOrUpdate(Product product)   
	{  
		if(product.getName().length()==0)
			throw new IllegalArgumentException("Product name can't be empty");
		productRepository.save(product);  
	}  

	//deleting a specific record  
	public void delete(long id)   
	{  
		productRepository.deleteById(id);  
	}  

	@Autowired
	EntityManager em;

    // constructor

    public List<Product> findProductByAuthorIdAndName(Long id, String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        Root<Product> product = cq.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();
        if(id>0) {
        	Predicate idPredicate = cb.equal(product.get("id"), id);
        	predicates.add(idPredicate);
        }
        if(name!=null) {
        	Predicate namePredicate = cb.like(product.get("name"), "%" + name + "%");
        	predicates.add(namePredicate);
        }
        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Product> query = em.createQuery(cq);
        return query.getResultList();
    }

}
