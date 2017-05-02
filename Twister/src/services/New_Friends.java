package services;

import org.json.JSONObject;

import serviceTools.Friend_Tools;
import serviceTools.Tools;

public class New_Friends {

	public static JSONObject addFriend(String login, String freindLog){
		
		boolean OK = false;
		
		/**
		 * TEST EXISTE FRIEND
		 */
		//test de temps d'inactivité
		if(!(Tools.activ(login))){
			Tools.desconect(login);
			return Tools.creationInfoJSONObject("ko", 22);						
		}
		
		if((OK = Tools.testUserNotExist(freindLog))){
			return Tools.creationInfoJSONObject("ko",13 );
		}
		
		
		/** AJOUTER UN AMI */
		
		JSONObject res = new JSONObject();
		res = Friend_Tools.creationSuivie(login, freindLog);
		return res;

	}
	

}
