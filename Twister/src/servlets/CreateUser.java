package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.Create_User;
 
/**
 * Servlet implementation class HelloWorld
 */
public class CreateUser extends HttpServlet {
 
 /**
 * Default constructor.
 */
 public CreateUser() {
 }
 
 /*
 * This method will handle all GET request.<
 */
 protected void doGet(HttpServletRequest request,
 HttpServletResponse response) throws ServletException, IOException {
 
	 
	// Map param = request.getParameterMap();
	 String nom = request.getParameter("nom");
	 String prenom = request.getParameter("prenom");
	 String login = request.getParameter("login");
	 String mdp = request.getParameter("mdp");
	 String adresse = request.getParameter("adresse");
		
	 JSONObject obj =  Create_User.Create_Users( nom , prenom ,login ,mdp ,adresse);
 	 
	 
 	response.setContentType( " text / plain " );
	PrintWriter out = response.getWriter ();
	out.println(obj);
 }
 
 

}






