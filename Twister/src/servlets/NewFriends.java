package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.Login_user;
import services.New_Friends;

public class NewFriends extends HttpServlet {

	
	 protected void doGet(HttpServletRequest request,
			 HttpServletResponse response) throws ServletException, IOException {
				 
				 String login = request.getParameter("login");
				 String friendLog = request.getParameter("friendLog");
				
				 JSONObject obj =  New_Friends.addFriend(login, friendLog);
			 	 
				 
			 	response.setContentType( " text / plain " );
				PrintWriter out = response.getWriter ();
				out.println( obj ); 
			 
			 }
}
