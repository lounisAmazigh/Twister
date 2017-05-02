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
import services.Liste_Message;
import services.Login_user;
import services.New_Comment;
 
/**
 * Servlet implementation class HelloWorld
 */
public class NewComment extends HttpServlet {
 
 /**
 * Default constructor.
 */
 public NewComment() {
 }
 
 /*
 * This method will handle all GET request.<
 */
 protected void doGet(HttpServletRequest request,
 HttpServletResponse response) throws ServletException, IOException {
	 
	 String login = request.getParameter("login");
	 int idMessage = Integer.parseInt(request.getParameter("idmsg"));
	 String comment = request.getParameter("comment");
	 
 	response.setContentType( " text / plain " );
	PrintWriter out = response.getWriter ();
	out.println( New_Comment.New_Comments(login ,idMessage,comment) );
 
 }
 

}