package services;


import org.json.JSONObject;

import serviceTools.Tools;
import serviceTools.User_Tools;

public class Log_Out_user {
	public static JSONObject logOut(String login){
		boolean OK=false;
		
		/**
		 * TEST ATTRIBUTS OK  
		 */
		
		//test de temps d'inactivité
		if(!(Tools.activ(login))){
			Tools.desconect(login);
			return Tools.creationInfoJSONObject("ko", 22);				
		}
		
		if(!(OK = User_Tools.testAttributsLogout(login))){
			return Tools.creationInfoJSONObject("ko", 7);
		}
		
		
		JSONObject obj = new JSONObject();
		obj = User_Tools.logOut_user(login);
		return obj;
		
	}

}
