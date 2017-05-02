package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.Create_User;
import services.Login_user;
 
/**
 * Servlet implementation class HelloWorld
 */
public class Login extends HttpServlet {
 
 /**
 * Default constructor.
 */
 public Login() {
 }
 
 /*
 * This method will handle all GET request.<
 */
 protected void doGet(HttpServletRequest request,
 HttpServletResponse response) throws ServletException, IOException {
	 
	 String login = request.getParameter("login");
	 String mdp = request.getParameter("mdp");
	
	 JSONObject obj =  Login_user.login(login, mdp);
 	 
	 
 	response.setContentType( " text / plain " );
	PrintWriter out = response.getWriter ();
	out.println( obj );
 
 }
 

}