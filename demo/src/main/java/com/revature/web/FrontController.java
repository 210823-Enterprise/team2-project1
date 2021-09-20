package com.revature.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// save the URI and rewrite it to determine what functionality the user is requesting based on URL
		final String URI = request.getRequestURI().replace("/FrontControllerDemo/", "");
		
		switch(URI) {
		case "users":
			RequestHelper.processUsers(request, response);
			break;
		case "error":
			RequestHelper.processError(request, response);
			break;
		default:
			RequestHelper.processError(request, response);
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final String URI = request.getRequestURI().replace("/FrontControllerDemo/", "");
		
		switch(URI) {
		case "newuser":
			RequestHelper.processNewUser(request, response);
			break;
		default:
			RequestHelper.processError(request, response);
			break;
		}
	}

}
