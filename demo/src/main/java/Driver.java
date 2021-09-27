
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.revature.annotations.Column;
import com.revature.annotations.Id;
import com.revature.exceptions.NotInCacheException;
import com.revature.models.Account;
import com.revature.objectmapper.ObjectCache;
import com.revature.objectmapper.ObjectMapper;
import com.revature.util.ConnectionFactory;
import com.revature.util.TransactionController;

public class Driver {
	public static void main(String[] args) {
		
		ObjectMapper om = new ObjectMapper();
		TransactionController tc = new TransactionController();
		Connection cn = ConnectionFactory.getConnection();
		
		System.out.println("============================================================================");
		System.out.println("========================= Transaction Demo =================================");
		System.out.println("============================================================================");
		System.out.println("\n======================= Database Start State ===============================");

		System.out.println("\nTable is empty\n");

		
		
		System.out.println("============================================================================");
		System.out.println("==========================Transaction Start=================================");
		tc.setTransaction(cn);
		Account a1 = new Account("Chase Checking", 50, 200.00);
		Account a2 = new Account("Chase Savings", 51, 700.00);
		Account a3 = new Account("USAA Checking", 52, 500.00);
		Account a4 = new Account("USAA Saving", 53, 300.00);
		List<Account> accounts = new ArrayList<>();
		accounts.add(a1);
		accounts.add(a2);
		accounts.add(a3);
		accounts.add(a4);
		System.out.println("\n============================================================================");
		System.out.println("======================= Accounts to be Added ===============================");
		for (Account a: accounts) {
			System.out.println(a);
		}
		System.out.println("\n============================================================================");
		System.out.println("=========== Accounts added to transaction, not yet committed ===============");
		for (Account a: accounts) {
			om.addObjectToDB(a, cn);
		}
		System.out.println("\n============================================================================");
		System.out.println("================ Database State, first transaction =========================");
		for (Object o:om.getListObjectFromDB(Account.class, cn)) {
			System.out.println(o);
		}
		tc.beginCommit(cn);
		System.out.println("\n============================================================================");
		System.out.println("================ Accounts added to second transaction ======================");
		accounts = new ArrayList<>();
		a1 = new Account("Placeholder Checking", 54, 200.00);
		a2 = new Account("Placeholder Savings", 55, 700.00);
		a3 = new Account("Somebank Checking", 56, 500.00);
		a4 = new Account("Somebank Saving", 57, 300.00);
		accounts.add(a1);
		accounts.add(a2);
		accounts.add(a3);
		accounts.add(a4);
		for (Account a: accounts) {
			System.out.println(a);
		}
		tc.setTransaction(cn);
		tc.setSavepoint("123", cn);
		for (Account a: accounts) {
			om.addObjectToDB(a, cn);
		}
		tc.setSavepoint("321", cn);
		System.out.println("\n============================================================================");
		System.out.println("================ Database State, Transaction Pending =======================");
		for (Object o:om.getListObjectFromDB(Account.class, cn)) {
			System.out.println(o);
		}
		tc.Rollback("123", cn);
		System.out.println("\n============================================================================");
		System.out.println("================ Database State, Transaction Rolled Back ===================");
		for (Object o:om.getListObjectFromDB(Account.class, cn)) {
			System.out.println(o);
		}
		tc.beginCommit(cn);
		System.out.println("\n============================================================================");
		System.out.println("================ Database State, Commit after Rollback =====================");
		for (Object o:om.getListObjectFromDB(Account.class, cn)) {
			System.out.println(o);
		}
		System.out.println("\n============================================================================");
		System.out.println("============Additional Features Not Shown in Servlet APP ===================");
		System.out.println("\n============================================================================");
		System.out.println("=======================Get Specific Columns=================================");
		for(Object o:om.getListObjectFromDB(Account.class, "ownerId,accountName",cn)) {
			System.out.println(o+"\n");
			for(Field f:o.getClass().getFields()) {
				try {
					if(f.get(o).equals(0) || f.get(o).equals(0.0)){}
					else {
						try {
							System.out.println(f.getAnnotation(Column.class).columnName()+" = "+f.get(o));
						} catch (NullPointerException e) {
							System.out.println(f.getAnnotation(Id.class).columnName()+" = "+f.get(o));
						}
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("\n============================================================================\n");
		}
		System.out.println("\n============================================================================");
		System.out.println("================== Get Specific Columns with Condition =====================");
		for(Object o:om.getListObjectFromDB(Account.class, "ownerId,accountName","ownerId= '50'",cn)) {
			System.out.println(o+"\n");
			for(Field f:o.getClass().getFields()) {
				try {
					if(f.get(o).equals(0) || f.get(o).equals(0.0)){}
					else {
						try {
							System.out.println(f.getAnnotation(Column.class).columnName()+" = "+f.get(o));
						} catch (NullPointerException e) {
							System.out.println(f.getAnnotation(Id.class).columnName()+" = "+f.get(o));
						}
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("\n============================================================================\n");
			System.out.println("===================== Caching: Loading Into Cache... =======================");
			
			try {
				ObjectCache.addAllFromDBToCache(Account.class, cn);
			} catch (NotInCacheException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("\n.....\n");
			System.out.println("===================== Caching: Printing from Cache... =======================");
			System.out.println(ObjectCache.getCache());
			
		}
		
	}
	
}
