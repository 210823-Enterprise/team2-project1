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
import com.revature.dao.UserDao;
import com.revature.models.User;
import com.revature.service.UserService;

public class RequestHelper {
	
	private static Logger log = Logger.getLogger(RequestHelper.class);
	private static UserService userv = new UserService(new UserDao());
	private static ObjectMapper om = new ObjectMapper();
	
	public static void processUsers(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		// 1. set the content type to return text to the browser
		response.setContentType("text/html");
		
		// 2. Get a list of all Userse in the Database
		List<User> allEmps = userv.findAll(); // create this method in the service layer
		
		// 3. Turn the list of Java Objects into a JSON string (using Jackson Databind Object Mapper).
		String json = om.writeValueAsString(allEmps);
		
		// 4. Use a Print Writer to write the objects to the response body seen in the browser
		PrintWriter out = response.getWriter();
		out.println(json);
		
	}
	
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
		User u = userv.confirmLogin(username, password);
		
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
	
	public static void processError(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		// if something goes wrong, redirect the user to a custom 404 error page
		request.getRequestDispatcher("error.html").forward(request, response);
	        /*
		 * Remember that the forward() method does NOT produce a new request,
		 * it just forwards it to a new resource, and we also maintain the URL
		*/
	}
}
