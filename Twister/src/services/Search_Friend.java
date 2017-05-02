package services;


import java.sql.SQLException;

import serviceTools.Tools;
import serviceTools.User_Tools;

import org.json.JSONException;
import org.json.JSONObject;

public class Search_Friend {
	 
	public Search_Friend()
	{
		
	}
	
	public static JSONObject Find_Users(String login) {
		
		boolean OK=false;		
		/**
		 * TEST LOGIN NOT EXIST
		 */
		if((OK = Tools.testUserNotExist(login))){
			return User_Tools.tout_les_users();
		}
		else{
			return Tools.creationInfoJSONObject("ok", 1);
		}
		
	}

}
