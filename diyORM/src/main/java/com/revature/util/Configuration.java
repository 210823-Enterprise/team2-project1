package com.revature.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class Configuration {
	
	//all jdbc properties needed to establish a connection with the db
	
	private String dbUrl;
	private String username;
	private String password;
	private List<MetaModel<Class<?>>> metaModelList;
	
	// maybe some connection pooling properties
	
	// this essentially does what the Hibernate.cfg.xml mapping property does!
	public Configuration addAnnotatedClass(Class annotatedClass) {
		
		if (metaModelList == null) {
			metaModelList = new LinkedList<>();
			
		}
		
		metaModelList.add(MetaModel.of(annotatedClass));
		
		// Create the of() method inside MetaModel to transform a class 
		// into an appropriate data model to be transposed into a relational db object.
		
		return this;
		
	}
	
	public List<MetaModel<Class<?>>> getMetaModels(){
		return (metaModelList==null) ? Collections.emptyList() : metaModelList;
	}

}
