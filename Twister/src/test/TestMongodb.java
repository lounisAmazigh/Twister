package test;
import java.awt.Cursor;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.Mongo;
import com.mongodb.WriteResult;

import serviceTools.Message_Tools;
import serviceTools.Tools;
import serviceTools.User_Tools;
import services.Delete_Comment;
import services.Delete_Friends;
import services.Delete_Message;
import services.Liste_Message;
import services.Log_Out_user;
import services.Login_user;
import services.New_Friends;
import services.New_Comment;
import services.new_Message;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

public class TestMongodb {
		
	public static void main(String[] args) {
		
		
		
		
		//System.out.println(Delete_Friends.deleteFreind("leGrand", "hakim"));
		//System.out.println(New_Friends.addFriend("leGrand", "hakim"));
		//System.out.println(User_Tools.abonne(39));
		//System.out.println(Message_Tools.creationComment("leGrand" , 1 , "c'est magique mec t'es le plus fort *.* !!!! "));
		//System.out.println(Delete_Message.suppMessage("leGrand",0));
		//System.out.println(Delete_Message.suppMessage("leGrand",2));
		//System.out.println(Friends_Messages.ListeMessage("leGrand","lePetitGrand"));
		//System.out.println(PostComment.PostComment("leGrand" ,0,"je suis usfgeren commentaire"));
		//Tools.udpdateAciv("leGrand");
		//System.out.println(Tools.activ("leGrand"));
		//Tools.desconect("leGrand");
		System.out.println(Delete_Friends.deleteFreind("scofild", "leGrand"));
		//System.out.println(Tools.activ("leGrand"));
		//System.out.println(Tools.getTimestamap().getTime());
		//System.out.println(Login_user.login("leGrand", "123456"));
		//System.out.println(Log_Out_user.logOut("leGrand"));
		//System.out.println(Tools.getLogOfId(40));
		//System.out.println(Tools.getTimestamap());
		//System.out.println(Delete_Comment.suppComment(5, 19));
		//System.out.println(Liste_Message.mesMessage("lePetitGrand"));
		//System.out.println(Message_Tools.creationComment("leGrand" , 5, "gjdjsldfj"));
		//System.out.println(New_Friends.addFriend("leGrand","hakim"));
	
	}
	}
