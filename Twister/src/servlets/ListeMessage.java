package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;


import services.Liste_Message;
import services.new_Message;
 
/**
 * Servlet implementation class HelloWorld
 */
public class ListeMessage extends HttpServlet {
 
 /**
 * Default constructor.
 */
 public ListeMessage() {
 }
 
 /*
 * This method will handle all GET request.<
 */
 protected void doGet(HttpServletRequest request,
 HttpServletResponse response) throws ServletException, IOException {
 
	 
	 String login = request.getParameter("login");
	
	JSONArray listMessage =  Liste_Message.mesMessage(login); 
	 
 	response.setContentType( " text / plain " );
 	PrintWriter out = response.getWriter ();
	out.println(listMessage.toString());
 }
 

}