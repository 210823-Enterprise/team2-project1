package com.revature;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.revature.dummymodels.Test;
import com.revature.objectmapper.ObjectMapper;
import com.revature.util.ColumnField;
import com.revature.util.Configuration;
import com.revature.util.ConnectionFactory;
import com.revature.util.MetaModel;

public class OrmDriver {

	public static void main(String[] args) {
		Configuration cfg = new Configuration();
		Connection cn = ConnectionFactory.getConnection();
		Test test = new Test(1, "ryan","pass");
		ObjectMapper om = new ObjectMapper();
		System.out.println(om.removeObjectFromDb(test, cn));
		
		//in our configuration object we want to add annotated class, without ever having to instantiate them
		// Hibernate maps class to apply reflection to (introspect)
		
		cfg.addAnnotatedClass(Test.class);
		
		// this is just to prove we have succesfully transformed test to a metamodel, readable by our framework
		//let's iterate over all meta models that exist in the config object
		for (MetaModel<?> metamodel:cfg.getMetaModels()) {
			System.out.printf("Printing metamodel for class: %s\n",metamodel.getClassName());
			
			List<ColumnField> columnFields = metamodel.getColumns();
			
			for(ColumnField cf : columnFields) {
				System.out.printf("Found a column field named %s of type %s, which maps to the DB column %s%n",cf.getName(),cf.getType(),cf.getColumnName());
			}
			
		}
	}

}
