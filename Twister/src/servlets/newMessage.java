package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.Create_User;
import services.new_Message;

public class newMessage extends HttpServlet{

	 public newMessage() {
	 }
	 
	 /*
	 * This method will handle all GET request.<
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		 String log = request.getParameter("log");
		 String message = request.getParameter("message");
			
		 JSONObject obj =  new_Message.NewMesssage(log,message);
	 
	 	response.setContentType( " text / plain " );
		PrintWriter out = response.getWriter ();
		out.println(obj);
	 }
}
