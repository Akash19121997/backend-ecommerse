package com.simplilearn.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.simplilearn.ecommerce.entity.Product;
import com.simplilearn.ecommerce.entity.ProductCategory;
@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer{

	
	private EntityManager entityManager;
	
	@Autowired
	public MyDataRestConfig(EntityManager theEntityManager) {
		entityManager=theEntityManager;
	}
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		// TODO Auto-generated method stub
		RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
		
		HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};
		
		//disable HTTP methods for Product : PUT, POST ,Delete
		config.getExposureConfiguration().forDomainType(Product.class)
		                                 .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
		                                 .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
		
		//disable HTTP methods for Product : PUT, POST ,Delete
				config.getExposureConfiguration().forDomainType(ProductCategory.class)
				                                 .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
				                                 .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
				// call internal helper method
				exposeIds(config);
			}
	private void exposeIds(RepositoryRestConfiguration config) {
		
		Set<EntityType<?>> entities =entityManager.getMetamodel().getEntities();
		
		List<Class> entityClasses =new ArrayList<>();
		
		for(EntityType tempEntityType : entities) {
			entityClasses.add(tempEntityType.getJavaType());
		}
		
		Class[] domianTypes = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domianTypes);
	}
	}
	

