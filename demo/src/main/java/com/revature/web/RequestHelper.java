package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.AccountDao;
import com.revature.dao.UserDao;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.service.AccountService;
import com.revature.service.UserService;

public class RequestHelper {
	
	private static Logger log = Logger.getLogger(RequestHelper.class);
	private static UserService userv = new UserService(new UserDao());
	private static AccountService aserv = new AccountService(new AccountDao());
	private static ObjectMapper om = new ObjectMapper();
	
	// This method will process a post request, so we can't capture parameters from the request like we would in a GET request
	public static void processLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		// we need to capture the user input from the request BODY 
		BufferedReader reader = request.getReader();
		StringBuilder s = new StringBuilder(); // username=bob&password=pass -> we need to extract bob and pass, but first we transform to string
		
		// transfer everything over to the string builder FROM te buffered reader
		String line = reader.readLine();
		
		while(line!= null) {
			s.append(line);
			line = reader.readLine();  //  req body looks like this: username=bob&password=secret
		}
		
		String body = s.toString(); 
		String [] sepByAmp = body.split("&"); // separate username=bob&password=pass -> [username=bob, password=pass]
		
		List<String> values = new ArrayList<String>();
		
		for (String pair : sepByAmp) { // each element in array looks like this
			values.add(pair.substring(pair.indexOf("=") + 1)); // trim each String element in the array to just value -> [bob, pass]
		}
		
		// capture the actual username and password values
		String username = values.get(0); // bob
		String password = values.get(1); // pass
		
		log.info("User attempted to login with username" + username);
		
		// call the confirmLogin() method and fetch the actual User object from the DB
		User u = null; // TODO = userv.confirmLogin(username, password);
		
		// return the user found and show the object in the browser
		if (u != null) {
			// grab the session & add the user to the session
			HttpSession session = request.getSession();
			session.setAttribute("user", u);
			
			// print the logged in user to the screen
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			
			// convert the object with the object mapper
			out.println(om.writeValueAsString(u));
			
			// log it!
			log.info("The user " + username + " has logged in.");
		} else {
			// if the returned object is null, return No Content status (successfull request, but no user found in DB).
			response.setStatus(204); 
		}
		
	}
	public static void processNewUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

	
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			User u = new User(0, username, password);
			
			HttpSession session = request.getSession();
			
			// 4. and send the custom villain to the session
			session.setAttribute("the-user", u);
	}
	
	public static void processError(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException, ServletException {
		
		// if something goes wrong, redirect the user to a custom 404 error page
		request.getRequestDispatcher("error.html").forward(request, response);
		response.getWriter().println(e.toString());
	}
	public static void processError(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		// if something goes wrong, redirect the user to a custom 404 error page
		request.getRequestDispatcher("error.html").forward(request, response);
		response.getWriter().println("It seems you have the wrong address...");
	}
	public static void findAllAccounts(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		List<Account> accounts = aserv.findAll();
		String json = om.writeValueAsString(accounts);
		PrintWriter out = response.getWriter();
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		out.print(json);
		out.flush();
	}
	public static void insertAccount(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		Account account = om.readValue(request.getReader(), Account.class);
		aserv.insert(account);
	}
	public static void updateAccount(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		Account account = om.readValue(request.getReader(), Account.class);		
		aserv.update(account);
	}
	public static void deleteAccount(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		int id = Integer.parseInt(request.getParameter("id"));
		Account a = new Account(id);
		aserv.delete(a);
	}
	public static void updateAutoCommit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		System.out.println(request.getParameter("choice"));
		
	}
}
