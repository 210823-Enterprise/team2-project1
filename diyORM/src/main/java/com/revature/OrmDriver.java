package com.revature;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.revature.dummymodels.Test;
import com.revature.exceptions.NotInCacheException;
import com.revature.objectmapper.ObjectCache;
import com.revature.objectmapper.ObjectGetter;
import com.revature.objectmapper.ObjectMapper;
import com.revature.util.Configuration;
import com.revature.util.ConnectionFactory;

public class OrmDriver {

	public static void main(String[] args) throws NotInCacheException {
		Configuration cfg = new Configuration();
		Connection cn = ConnectionFactory.getConnection();
		List<Test> tests = new ArrayList<Test>();
//		for (int i =0; i<10;i++) {
//			String user = "user"+i;
//			String pass = "pass"+i;
//			tests.add(new Test(i,user,pass));
//		}
		ObjectMapper om = new ObjectMapper();
//		for (Test test:tests) {
//			System.out.println("===================================");
//			System.out.println(om.addObjectToDB(test,cn));
//			try {
//				cn.commit();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		ObjectCache.emptyCache();
//		ObjectCache.addAllFromDBToCache(Test.class, cn);
//		System.out.println(ObjectCache.getCache());
		
		System.out.println(om.getListObjectFromDB(Test.class, "username", "username='ryan'", cn));
		
		//System.out.println(om.removeObjectFromDb(test, cn));
		
		//in our configuration object we want to add annotated class, without ever having to instantiate them
		// Hibernate maps class to apply reflection to (introspect)
		
		cfg.addAnnotatedClass(Test.class);
		
		// this is just to prove we have succesfully transformed test to a metamodel, readable by our framework
		//let's iterate over all meta models that exist in the config object
//		for (MetaModel<?> metamodel:cfg.getMetaModels()) {
//			System.out.printf("Printing metamodel for class: %s\n",metamodel.getClassName());
//			
//			List<ColumnField> columnFields = metamodel.getColumns();
//			
//			for(ColumnField cf : columnFields) {
//				System.out.printf("Found a column field named %s of type %s, which maps to the DB column %s%n",cf.getName(),cf.getType(),cf.getColumnName());
//			}
//			
//		}
	}

}
