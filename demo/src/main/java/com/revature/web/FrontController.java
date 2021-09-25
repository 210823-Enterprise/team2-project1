package com.revature.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final String URI = request.getRequestURI().replace("/T2-Demo/", "");
		
		switch(URI) {
		case "findAll":
			RequestHelper.findAllAccounts(request, response);
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

		final String URI = request.getRequestURI().replace("/T2-Demo/", "");
		
		switch(URI) {
		case "insertAccount":
			RequestHelper.insertAccount(request, response);
			break;
		default:
			RequestHelper.processError(request, response);
			break;
		}
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final String URI = request.getRequestURI().replace("/T2-Demo/", "");
		
		switch(URI) {
		case "deleteAccount":
			RequestHelper.deleteAccount(request, response);
			break;
		default:
			RequestHelper.processError(request, response);
			break;
		}
	}
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final String URI = request.getRequestURI().replace("/T2-Demo/", "");
		
		switch(URI) {
		case "updateAccount":
			RequestHelper.updateAccount(request, response);
			break;
		default:
			RequestHelper.processError(request, response);
			break;
		}
	}
}
