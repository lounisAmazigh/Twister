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

import services.Search_Friend;
import services.Create_User;
 
/**
 * Servlet implementation class HelloWorld
 */
public class SearchFriend extends HttpServlet {
 
 /**
 * Default constructor.
 */
 public SearchFriend() {
 }
 
 /*
 * This method will handle all GET request.<
 */
 protected void doGet(HttpServletRequest request,
 HttpServletResponse response) throws ServletException, IOException {
 
	 
	// Map param = request.getParameterMap();
	 String login = request.getParameter("login");
	
		
	 JSONObject obj =  Search_Friend.Find_Users(login);
 	 
	 
 	response.setContentType( " text / plain " );
	PrintWriter out = response.getWriter ();
	out.println(obj);
 }
 
 

}

