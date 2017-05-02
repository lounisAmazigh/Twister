package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.*;

 

public class LogOut extends HttpServlet{
	
	public LogOut(){
		
	}
	
	 protected void doGet(HttpServletRequest request,
			 HttpServletResponse response) throws ServletException, IOException {
		 
		 String login = request.getParameter("login");
		 
		 JSONObject obj =  Log_Out_user.logOut(login);
	 	 
		 
		 	response.setContentType( " text / plain " );
			PrintWriter out = response.getWriter ();
			out.println(obj); 
		 
		 
		 
	 }

}
