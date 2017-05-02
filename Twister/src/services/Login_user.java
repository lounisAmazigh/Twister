package services;


import org.json.JSONException;
import org.json.JSONObject;

import serviceTools.Tools;
import serviceTools.User_Tools;

public class Login_user {
	
	public static JSONObject login(String login , String mdp) {
		/* Test attribut not Null */
		JSONObject objj = new JSONObject();
		boolean OK=false;
	
		/**
		 * TEST ATTRIBUTS OK  
		 */
		
		if(!(OK = User_Tools.testAttributsLogin(login, mdp))){
			Tools.creationJSONObject("ko", 0);
			return Tools.creationInfoJSONObject("ko", 7);
		}
		
		/**
		 * TEST LOGIN NOT EXIST
		 */
		if((OK = Tools.testUserNotExist(login))){
			Tools.creationJSONObject("ko", 2);
			return Tools.creationInfoJSONObject("ko", 3);
		}
		
		/**
		 * TEST MOT DE PASSE 
		 */
		JSONObject obj = new JSONObject();
		obj = User_Tools.test_mdp(login , mdp);
		try {
			if(obj.getInt("1")==10){
				objj = User_Tools.CreateSession(login);
				return objj;
			}
			else
				return Tools.creationInfoJSONObject("ko", 2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("je suis la");
		}
		return obj;
		
		
	}

}
