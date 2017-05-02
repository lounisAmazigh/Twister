package test;

import javax.naming.spi.DirStateFactory.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import serviceTools.Friend_Tools;
import serviceTools.Tools;
import serviceTools.User_Tools;
import services.Search_Friend;
import services.Create_User;
import services.Log_Out_user;
import services.Login_user;
import services.New_Friends;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import BD.Database;

public class TestMySql {

	public static void main(String[] args) throws JSONException, InstantiationException, IllegalAccessException, SQLException {
		
		//System.out.println(Login_user.login("toto","azerty"));
		System.out.println(Search_Friend.Find_Users("bylka"));
		//System.out.println(Log_Out_user.logOut("toto"));
		//System.out.println(New_Friends.addFreind("toto", "leGrand"));
	}
}


//System.out.println("icikan "+Tools.CreateSession("toto"));
/*JSONObject o = Tools.logOut_user("toto");
System.out.println(o);*/
//res1 = Tools.creationJSONObject("LouBe" , 1);

//System.out.println(res1.getDouble("LouBe"));
	

	
	