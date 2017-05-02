package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.Delete_Friends;

import services.Login_user;

public class DeleteFriends  extends HttpServlet {

	
	 protected void doGet(HttpServletRequest request,
			 HttpServletResponse response) throws ServletException, IOException {
				 
				 String login = request.getParameter("login");
				 String friendLog = request.getParameter("friendLog");
			
				 JSONObject obj =  Delete_Friends.deleteFreind(login, friendLog);
			 	 
				 
			 	response.setContentType( " text / plain " );
				PrintWriter out = response.getWriter ();
				out.println(obj ); 
			 
			 }
}

