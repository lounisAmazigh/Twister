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

import services.Get_Messages;
import services.Liste_Message;
import services.new_Message;
 
/**
 * Servlet implementation class HelloWorld
 */
public class GetMessage extends HttpServlet {
 
 /**
 * Default constructor.
 */
 public GetMessage() {
 }
 
 /*
 * This method will handle all GET request.<
 */
 protected void doGet(HttpServletRequest request,
 HttpServletResponse response) throws ServletException, IOException {
 
	 
	 String login = request.getParameter("login");
	 int idMin = Integer.parseInt(request.getParameter("idMin"));
	
	JSONArray listMessage =  Get_Messages.getMessage(login, idMin); 
	 
 	response.setContentType( " text / plain " );
 	PrintWriter out = response.getWriter ();
	out.println(listMessage.toString());
 }
 

}