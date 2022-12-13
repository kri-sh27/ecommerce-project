package com.nttdata.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.HttpMethods;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.nttdata.ecommerce.entity.Product;
import com.nttdata.ecommerce.entity.ProductCategory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
	
	
	private EntityManager entityManager;
	
	@Autowired
	public MyDataRestConfig(EntityManager theEntityManager) {
		entityManager=theEntityManager;
		
	}
	

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		// TODO Auto-generated method stub
//		RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
	
		
		HttpMethod[] theUnsupportedActions = {
			HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE	
		};
		
		//disable HTTP methods for Product: PUT,POST and DELETE
		config.getExposureConfiguration()
		.forDomainType(Product.class)
		.withItemExposure((metdata, httpMethods)-> httpMethods.disable(theUnsupportedActions))
		.withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
		
		//disable HTTP methods for ProductCategory: PUT,POST and DELETE
				config.getExposureConfiguration()
				.forDomainType(ProductCategory.class)
				.withItemExposure((metdata, httpMethods)-> httpMethods.disable(theUnsupportedActions))
				.withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
				
	//call an intenal helper method
				exposedIds(config);
				
	}
	
	private void exposedIds(RepositoryRestConfiguration config) {
		// expose entity ids
		
		// -get a list of all entity classes from the entity manager 
		Set<EntityType<?>> entities=entityManager.getMetamodel().getEntities();
		
		//create an array of the entity types
		
		List<Class> entityClasses=new ArrayList<>();
	
		//get the entity types for the entities
		
		
		for (EntityType tempEntityType:entities) {
			entityClasses.add(tempEntityType.getJavaType());
		}
		
		// expose the entityt ids for the array of entity/domin types
		
		Class[] domainTypes = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes);
		
	}
}
